package com.google.gwt.artvan.client;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.code.gwt.geolocation.client.PositionOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ArtFinder implements EntryPoint {
	// stockwatcher items
	private final int REFRESH_INTERVAL = 1200000; // ms
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable artFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button findArtButton = new Button("findArt");
	private Button deleteAllArtButton = new Button("DELETE_ALL_Art");
	private HorizontalPanel artPanel = new HorizontalPanel();
	private Button addStockButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();
	private ArrayList<String> stocks = new ArrayList<String>();
	private StockPriceServiceAsync stockPriceSvc = GWT
			.create(StockPriceService.class);
	// private ArtInformationServiceAsync artInfoService =
	// GWT.create(ArtInformationService.class);
	private ArtList artlist = new ArtList();
	// login items
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Sign in to your GOOG account to access ArtFinder Application");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	private Label userName = new Label("unknown");

	private VerticalPanel geolocationPanel = new VerticalPanel();

	private final StockServiceAsync stockService = GWT
			.create(StockService.class);

	/**
	 * Entry point method
	 */

	public void onModuleLoad() {
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
					@Override
					public void onFailure(Throwable caught) {
						handleError(caught);
					}

					public void onSuccess(LoginInfo result) {

						loginInfo = result;
						if (loginInfo.isLoggedIn()) {
							System.out.println("user: \""
									+ loginInfo.getNickname()
									+ "\"  is logged in, loading ArtWatcher");
							userName.setText(loginInfo.getNickname());
							loadArtWatcher();
						} else {
							System.out
									.println("user not logged in, loading login page");
							loadLogin();
						}
					}
				});
	}

	protected void loadLogin() {
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("artList").add(loginPanel);

	}

	private void loadArtWatcher() {
		// set up signout link
		signOutLink.setHref(loginInfo.getLogoutUrl());
		signOutLink.setVisible(true);
		// create table for  data

		artFlexTable.setText(0, 0, "Name");
		artFlexTable.setText(0, 1, "Lat/Long");
		artFlexTable.setText(0, 2, "Description");
		artFlexTable.setText(0, 3, "Visits");

		// Add styles to elements in the table.
		artFlexTable.setCellPadding(6);

		// Add styles to elements in the stock list table.
		artFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		artFlexTable.addStyleName("watchList");
		artFlexTable.getCellFormatter().addStyleName(0, 1,
				"watchListNumericColumn");
		artFlexTable.getCellFormatter().addStyleName(0, 2,
				"watchListNumericColumn");
		artFlexTable.getCellFormatter().addStyleName(0, 3,
				"watchListRemoveColumn");

		//loadStocks();

		// Assemble Add Stock panel
		//addPanel.add(addStockButton);
		//addPanel.add(newSymbolTextBox);
		//addPanel.addStyleName("addPanel");

		artPanel.add(findArtButton);
		artPanel.add(deleteAllArtButton);
		
		// Assemble Main Panel
		mainPanel.add(signOutLink);
		mainPanel.add(userName);
		mainPanel.add(artPanel);
		mainPanel.add(artFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdatedLabel);

		// Associate Main panel with the HTML host page
		RootPanel.get("artList").add(mainPanel);

		// Move cursor focus to input box
		newSymbolTextBox.setFocus(true);

		// test geolocation handler
		mainPanel.add(geolocationPanel);
		geolocationPanel.add(new Label("Geolocation provider: "
				+ Geolocation.getProviderName()));
		geolocationPanel.add(new Label("GWT strongname: "
				+ GWT.getPermutationStrongName()));

		Label l1 = new Label("Obtaining Geolocation...");
		geolocationPanel.add(l1);
		if (!Geolocation.isSupported()) {
			l1.setText("Obtaining Geolocation FAILED! Geolocation API is not supported.");
			return;
		}
		final Geolocation geo = Geolocation.getGeolocation();
		if (geo == null) {
			l1.setText("Obtaining Geolocation FAILED! Object is null.");
			return;
		}
		l1.setText("Obtaining Geolocation DONE!");
		obtainPosition(geolocationPanel, geo);

		// Listen for mouse events on add button
		addStockButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addStock();
			}
		});

		findArtButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				refreshArtList();
			}
		});
		
		deleteAllArtButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				artlist.deleteAllArt();
				refreshArtList();
			}
		});

		// Listen for keyboard
		newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				// fix for return on linux, get real keycode, not getChar
				int keycode = event.getNativeEvent().getKeyCode();
				System.out.println("keypress " + event.getCharCode() + ": "
						+ keycode);
				if (keycode == KeyCodes.KEY_ENTER) {
					addStock();
				}

			}

		});

		// setup timer for refreshing prices
//		Timer refreshTimer = new Timer() {
//
//			@Override
//			public void run() {
//				refreshWatchList();
//
//			}
//
//		};
//		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	}

	private void obtainPosition(final VerticalPanel geolocationPanel2,
			Geolocation geo) {
		final Label l2 = new Label("Obtaining position (timeout: 15 sec)...");
		geolocationPanel2.add(l2);

		geo.getCurrentPosition(new PositionCallback() {
			public void onFailure(PositionError error) {
				String message = "";
				switch (error.getCode()) {
				case PositionError.UNKNOWN_ERROR:
					message = "Unknown Error";
					break;
				case PositionError.PERMISSION_DENIED:
					message = "Permission Denied";
					break;
				case PositionError.POSITION_UNAVAILABLE:
					message = "Position Unavailable";
					break;
				case PositionError.TIMEOUT:
					message = "Time-out";
					break;
				default:
					message = "Unknown error code.";
				}
				l2.setText("Obtaining position FAILED! Message: '"
						+ error.getMessage() + "', code: " + error.getCode()
						+ " (" + message + ")");
			}

			public void onSuccess(Position position) {
				l2.setText("Obtaining position DONE:");
				Coordinates c = position.getCoords();
				geolocationPanel2.add(new Label("lat, lon: " + c.getLatitude()
						+ ", " + c.getLongitude()));
				geolocationPanel2.add(new Label("Accuracy (in meters): "
						+ c.getAccuracy()));
				geolocationPanel2.add(new Label("Altitude: "
						+ (c.hasAltitude() ? c.getAltitude() : "[no value]")));
				geolocationPanel2
						.add(new Label("Altitude accuracy (in meters): "
								+ (c.hasAltitudeAccuracy() ? c
										.getAltitudeAccuracy() : "[no value]")));
				geolocationPanel2.add(new Label("Heading: "
						+ (c.hasHeading() ? c.getHeading() : "[no value]")));
				geolocationPanel2.add(new Label("Speed: "
						+ (c.hasSpeed() ? c.getSpeed() : "[no value]")));
			}
		}, PositionOptions.getPositionOptions(false, 15000, 30000));
	}

	private void loadStocks() {
		stockService.getStocks(new AsyncCallback<String[]>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(String[] symbols) {
				displayStocks(symbols);
			}
		});
	}

	protected void displayStocks(String[] symbols) {
		for (String symbol : symbols) {
			displayStock(symbol);
		}
	}

	protected void addStock() {
		final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
		newSymbolTextBox.setFocus(true);

		if (!symbol.matches("^[0-9A-Za-z\\.]{1,10}$")) {
			Window.alert("'" + symbol + "' is not a valid symbol");
			newSymbolTextBox.selectAll();
			return;
		}

		newSymbolTextBox.setText("");

		// dont add stock if its already in table
		if (stocks.contains(symbol))
			return;

		// displayStock(symbol);
		addStock(symbol);
	}

	private void addStock(final String symbol) {
		stockService.addStock(symbol, new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(Void ignore) {
				displayStock(symbol);
			}
		});
	}

	private void displayStock(final String symbol) {
		// add stock
		int row = artFlexTable.getRowCount();
		stocks.add(symbol);
		artFlexTable.setText(row, 0, symbol);
		artFlexTable.setWidget(row, 2, new Label());
		artFlexTable.getCellFormatter().addStyleName(row, 1,
				"watchListNumericColumn");
		artFlexTable.getCellFormatter().addStyleName(row, 2,
				"watchListNumericColumn");
		artFlexTable.getCellFormatter().addStyleName(row, 3,
				"watchListRemoveColumn");

		// add button to remove stock
		Button removeStockButton = new Button("x");
		removeStockButton.addStyleDependentName("remove");

		removeStockButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				removeStock(symbol);
			}

		});

		artFlexTable.setWidget(row, 3, removeStockButton);
		// get stock price
		refreshWatchList();
	}

	protected void removeStock(final String symbol) {
		stockService.removeStock(symbol, new AsyncCallback<Void>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(Void ignore) {
				undisplayStock(symbol);
			}
		});
	}

	private void undisplayStock(final String symbol) {
		int removedIndex = stocks.indexOf(symbol);
		stocks.remove(removedIndex);
		artFlexTable.removeRow(removedIndex + 1);
	}

	private void refreshWatchList() {

		if (stockPriceSvc == null) {
			stockPriceSvc = GWT.create(StockPriceService.class);
		}

		// Set up the callback object.
		AsyncCallback<StockPrice[]> callback = new AsyncCallback<StockPrice[]>() {
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
			}

			public void onSuccess(StockPrice[] result) {
				updateTable(result);
			}
		};

		// Make the call to the stock price service.
		stockPriceSvc.getPrices(stocks.toArray(new String[0]), callback);

	}

	private void refreshArtList() {

		Vector<ArtInformation> resultVector = artlist.searchByLocation(15, 15);
		ArtInformation[] resultarray = new ArtInformation[resultVector.size()];
		resultarray = resultVector.toArray(resultarray);
		updateTable(resultarray);

	}

	@SuppressWarnings("deprecation")
	private void updateTable(StockPrice[] prices) {
		for (int i = 0; i < prices.length; i++) {
			updateTable(prices[i]);
		}

		lastUpdatedLabel.setText("Last update : "
				+ DateTimeFormat.getMediumDateTimeFormat().format(new Date()));

	}

	private void updateTable(ArtInformation[] artInfo) {
		int existing_rowcount = artFlexTable.getRowCount();
		
		for (int i = 0; i < artInfo.length; i++) {
			updateTable(artInfo[i], i + 1);
		}
		// we have fewer rows than before, clear the excess
		for (int i = artInfo.length; i< existing_rowcount; i++){
			artFlexTable.removeRow(i);
		}
		lastUpdatedLabel.setText("Last update : "
				+ DateTimeFormat.getMediumDateTimeFormat().format(new Date()));

	}

	private void updateTable(ArtInformation artInfo, int row) {

		String priceText = " " + artInfo.getLat() + " " + artInfo.getLng();
		String changeText = artInfo.getDescription();

		artFlexTable.setText(row, 0, artInfo.getName());
		artFlexTable.setText(row, 1, priceText);
		artFlexTable.setText(row, 2, changeText);
		artFlexTable.setText(row, 3, "" + artInfo.getVisits());
	}

	private void updateTable(StockPrice stockPrice) {
		if (!stocks.contains(stockPrice.getSymbol())) {
			return;
		}

		int row = stocks.indexOf(stockPrice.getSymbol()) + 1;

		String priceText = NumberFormat.getFormat("#,##0.00").format(
				stockPrice.getPrice());
		NumberFormat changeFormat = NumberFormat
				.getFormat("+#,##0.00;-#,##0.00");
		String changeText = changeFormat.format(stockPrice.getChange());
		String changePercentText = changeFormat.format(stockPrice
				.getChangePercent() / stockPrice.getPrice());

		artFlexTable.setText(row, 1, priceText);
		// insert label into element and update label instead of setText of
		// flextable element
		// artFlexTable.setText(row, 2, changeText + " (" + changePercentText +
		// "%)");
		Label changeWidget = (Label) artFlexTable.getWidget(row, 2);
		changeWidget.setText(changeText + " (" + changePercentText + "%)");

		// Change the color of text in the Change field based on its value.
		String changeStyleName = "noChange";
		if (stockPrice.getChangePercent() < -0.1f) {
			changeStyleName = "negativeChange";
		} else if (stockPrice.getChangePercent() > 0.1f) {
			changeStyleName = "positiveChange";
		}

		changeWidget.setStyleName(changeStyleName);
	}

	private void handleError(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(loginInfo.getLogoutUrl());
		}
	}

}