/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.web.controller;

import it.usr.web.domain.AppUser;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.slf4j.Logger;
import it.usr.web.producer.AppLogger;

/**
 *
 * @author riccardo.iovenitti
 */
public abstract class BaseController implements Serializable {
    public final static long serialVersionUID = 1L;
    public final static String SAME_VIEW = null;
    public final static String CURRENCY_PATTERN = "#,##0.00 €";
    @Inject
    @AppLogger
    Logger baseLogger;

    public Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public AppUser getUtente() {
        return (AppUser) getSessionMap().get(getSessionClassName(AppUser.class));
    }

    public String redirect(String path) {
        if (path == null) {
            return null;
        }

        if (!path.contains("?")) {
            return path + "?faces-redirect=true";
        }

        if (!path.contains("faces-redirect=true")) {
            return path + "&faces-redirect=true";
        }

        return path;
    }

    public void redirect(Redirector path) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(path.go());
    }
    
    public String viewParam(String path, String paramName, Object paramValue, Object... params) {
        if (path == null) {
            return path;
        }
        if (paramName == null) {
            throw new IllegalArgumentException("paramName null.");
        }

        path += !path.contains("?") ? "?" : "&";
        if (!path.contains("includeViewParams")) {
            path += "includeViewParams=true&";
        }

        path += paramName + "=" + String.valueOf(paramValue);

        if (params.length > 0) {
            if (params.length % 2 != 0) {
                throw new IllegalArgumentException("optional params are odd");
            }
            for (int i = 0; i < params.length; i = +2) {
                path += "&" + params[i] + "=" + params[i + 1];
            }
        }

        return path;
    }

    public String decimalFormat(Object data, String format) {
        try {
            return new DecimalFormat(format).format(data);
        }
        catch(RuntimeException e) {
            String dtype = (data!=null) ? data.getClass().toString() : "null-reference";
            baseLogger.error("Errore conversione [{}] tipo [[{}] in formato [{}]. Eccezione [{}]", data, dtype, format, e);
            throw e;
        }
    }

    public String dateFormat(Date d) {
        return new SimpleDateFormat("dd-MM-yyyy").format(d);
    }
    
    public static String getSessionClassName(Class c) {
        if (c == null) {
            throw new IllegalArgumentException("class object cannot be null");
        }

        String cName = c.getSimpleName();
        return cName.substring(0, 1).toLowerCase() + cName.substring(1);
    }

    public void putIntoSession(Object o) {
        if (o == null) {
            return;
        }

        getSessionMap().put(getSessionClassName(o.getClass()), o);
    }

    public void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public Throwable unrollException(Throwable exception,
            Class<? extends Throwable> expected) {

        while (exception != null && exception != exception.getCause()) {
            if (expected.isInstance(exception)) {
                return exception;
            }
            exception = exception.getCause();
        }
        return null;
    }
    
    public String sanitizePath(String s) {
        return (s!=null) ? s.replaceAll("[\\/|\\\\|\\?|\\<|\\>|\\*|\\:|\\|]+", "_") : null;
    }
    
    public boolean isView(String viewId) {
        String currentViewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        String[] pathParts = currentViewId.split("/");        
        return pathParts[pathParts.length-1].equalsIgnoreCase(viewId);
    }
    
    protected int[] annoMeseSplit(String annoMese) {
        int[] split = new int[2];
        if(annoMese.length()>6) {            
            String[] _am = annoMese.split("/");
            split[1] = Integer.parseInt(_am[0]); // anno
            split[0] = Integer.parseInt(_am[1]); // mese            
        }
        else {
            split[0] = Integer.parseInt(annoMese.substring(4));
            split[1] = Integer.parseInt(annoMese.substring(0, 4));
        }
        return split;
    }
    
    public boolean isEmpty(String s) {
        return s!=null ? s.trim().length()==0 : true;
    }
    
    public String formatAnnoMese(String annoMese) {
        int[] am = annoMeseSplit(annoMese);
        return am[1]+"/"+((am[0]<10) ? "0" : "")+am[0];
    }
    
    public int percentuale(int max, int perc) {
        return (int)Math.ceil(max/100.0f*perc);
    }
    
    public void addMessage(Message m) {
        addMessage(null, m);
    }
    
    public void addMessage(String clientId, Message m) {
        FacesContext.getCurrentInstance().addMessage(clientId, m.build());
    }
    
    public static class Message {
        private final FacesMessage.Severity severity;
        private String summary;
        private String detail = null;
        
        private Message(FacesMessage.Severity severity, String summary, String detail) {
            this.severity = severity;
            this.summary = summary;
            this.detail = detail;
        }
        
        public static Message info(String detail) {
            return new Message(FacesMessage.SEVERITY_INFO, "Informazione", detail);
        }
        
        public static Message warn(String detail) {
            return new Message(FacesMessage.SEVERITY_WARN, "Attenzione", detail);
        }
        
        public static Message fatal(String detail) {
            return new Message(FacesMessage.SEVERITY_FATAL, "Errore fatale", detail);
        }
        
        public static Message error(String detail) {
            return new Message(FacesMessage.SEVERITY_ERROR, "Errore", detail);
        }
        
        public Message withSummary(String summary) {
            this.summary = summary;
            return this;
        }
        
        public FacesMessage build() {
            return new FacesMessage(severity, summary, detail);
        }
    }
    
    public int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }
}
