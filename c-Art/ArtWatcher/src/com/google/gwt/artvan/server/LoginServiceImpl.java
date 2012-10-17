package com.google.gwt.artvan.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.artvan.client.LoginInfo;
import com.google.gwt.artvan.client.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginInfo login(String requestUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		LoginInfo loginInfo = new LoginInfo();
		
		System.out.println("requestUri =" + requestUri);
		if (user!=null){
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
			System.out.println("logout url set to : " + loginInfo.getLogoutUrl());
		}else{
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
			System.out.println("login url set to : " + loginInfo.getLoginUrl());
		}
		return loginInfo;
	}

}
