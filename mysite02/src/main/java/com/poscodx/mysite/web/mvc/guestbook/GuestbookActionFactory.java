package com.poscodx.mysite.web.mvc.guestbook;

import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;

import com.poscodx.mysite.dao.GuestbookDao;
import com.poscodx.mysite.vo.GuestbookVo;
import com.poscodx.mysite.web.mvc.main.MainAction;
import com.poscodx.mysite.web.mvc.user.JoinAction;
import com.poscodx.mysite.web.mvc.user.JoinFormAction;
import com.poscodx.mysite.web.mvc.user.JoinSuccessAction;
import com.poscodx.mysite.web.mvc.user.LoginAction;
import com.poscodx.mysite.web.mvc.user.LoginformAction;
import com.poscodx.mysite.web.mvc.user.LogoutAction;
import com.poscodx.mysite.web.mvc.user.UpdateAction;
import com.poscodx.mysite.web.mvc.user.UpdateformAction;
import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class GuestbookActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("insert".equals(actionName)) {
			action = new InsertAction();	
		}else if ("delete".equals(actionName)) {
			action = new DeleteAction();
		}else if ("deleteform".equals(actionName)) {
			action = new DeleteformAction();
		}else {
			action = new IndexAction();
		}
	 	return action;
	}

}
