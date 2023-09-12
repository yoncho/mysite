package com.poscodx.mysite.web.mvc.board;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;
import com.poscodx.web.mvc.utils.WebUtil;

public class BoardActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("writeform".equals(actionName)) {
			action = new WriteformAction();
		}else if("write".equals(actionName)){
			action = new WriteAction();	
		}else if("view".equals(actionName)){
			action = new ViewAction();
		}else if("delete".equals(actionName)){
			action = new DeleteAction();
		}else if("modifyform".equals(actionName)){
			action = new ModifyformAction();
		}else if("modify".equals(actionName)){
			action = new ModifyAction();
		}else {
			action = new IndexAction();	
		}
	 	return action;
	}

}
