package com.poscodx.mysite.web.mvc.user;

import com.poscodx.mysite.web.mvc.main.MainAction;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class UserActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		//이렇게 mvc로 필요한 aciton을 모듈화해놔도,,, 코드가 지저분함
	 	Action action = null;
	 	if("joinform".equals(actionName)) {
	 		action = new JoinFormAction();
	 	}else if("join".equals(actionName)) {
	 		action = new JoinAction();
	 		
	 	}else if ("joinsuccess".equals(actionName)) {
	 		action = new JoinSuccessAction();
	 	}else if ("loginform".equals(actionName)) {
	 		action = new LoginformAction();
	 	}else if ("login".equals(actionName)) {
	 		action = new LoginAction();
	 	}else if ("logout".equals(actionName)) {
	 		action = new LogoutAction();
	 	}else {
	 		action = new MainAction(); //main으로 가는 action
	 	}
	 	return action;
	}

}
