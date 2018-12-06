package com.profsoft.smsnotifications.model.base.criteria;

/**
 *
 * @author Maroo
 */
public class OracleHelper {

    public static String[] convertMaskList(String maskList) {
        if (maskList != null) {
            String masks[] = maskList.split(",");
            for (int i = 0; i < masks.length; i++) {
                masks[i] = convertMask(masks[i]);
                masks[i] = ("%" + masks[i] + "%").replaceAll("%%", "%");
            }
            return masks;
        }
        return null;
    }

    public static String convertMask(String mask) {
        return mask.trim().replaceAll("\\*", "%").replaceAll("\\?", "_");
    }

}
