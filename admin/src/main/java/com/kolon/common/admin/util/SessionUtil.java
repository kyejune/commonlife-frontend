package com.kolon.common.admin.util;

import javax.servlet.http.HttpSession;

/**
 * Created by agcdDev on 2017-07-07.
 */
public class SessionUtil {
    /**
     * SESSION SET
     * @param session
     * @param ikenId
     * @param sessionId
     * @param sessionValues
     */
    public static void setSession(HttpSession session
                                , String ikenId
                                , String sessionId
                                , String sessionValues)
    {
        String strSessionId = ikenId + "_" + sessionId;

        session.setAttribute(strSessionId, sessionValues);

    }

    /**
     * SESSION GET
     * @param session
     * @param ikenId
     * @param sessionId
     * @return
     */
    public static String getSession(HttpSession session
                                  , String ikenId
                                  , String sessionId)
    {
        String strSessionId = ikenId + "_" + sessionId;

        String sessionValues = (String) session.getAttribute(strSessionId);

        return sessionValues;
    }

    /**
     * SESSION REMOVE
     * @param session
     * @param ikenId
     * @param sessionId
     */
    public static void removeSession(HttpSession session
                                   , String ikenId
                                   , String sessionId)
    {
        String strSessionId = ikenId + "_" + sessionId;
        session.removeAttribute(strSessionId);
    }

}
