package com.realdolmen.beans;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@SessionScoped
@Named
public class SessionBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public String getEmail() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null && session.getAttribute("email") != null) {
			return session.getAttribute("email").toString();
		} else {
			return null;
		}
	}

	public String getUId() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("userid");
		else
			return null;
	}

	public boolean loggedOn() {
		return getEmail() != null;
	}
}