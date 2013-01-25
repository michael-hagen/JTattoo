/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*  
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*  
* see: gpl-2.0.txt
* 
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
* 
* see: lgpl-2.0.txt
* see: classpath-exception.txt
* 
* Registered users could also use JTattoo under the terms and conditions of the 
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*  
* see: APACHE-LICENSE-2.0.txt
*/

package com.jtattoo.plaf;

import java.awt.Color;

/**
 * @author Michael Hagen
 */
public class ColorHelper {

    private ColorHelper() {
    }

    public static Color createColor(int r, int g, int b) {
        return new Color(((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF)));
    }

    public static Color[] createColorArr(Color c1, Color c2, int steps) {
        if (c1 == null || c2 == null) {
            return null;
        }

        Color colors[] = new Color[steps];
        double r = c1.getRed();
        double g = c1.getGreen();
        double b = c1.getBlue();
        double dr = ((double) c2.getRed() - r) / steps;
        double dg = ((double) c2.getGreen() - g) / steps;
        double db = ((double) c2.getBlue() - b) / steps;
        colors[0] = c1;
        for (int i = 1; i < steps - 1; i++) {
            r += dr;
            g += dg;
            b += db;
            colors[i] = createColor((int) r, (int) g, (int) b);
        }
        colors[steps - 1] = c2;
        return colors;
    }

    public static Color brighter(Color c, double p) {
        if (c == null) {
            return null;
        }
        
        double r = c.getRed();
        double g = c.getGreen();
        double b = c.getBlue();

        double rd = 255.0 - r;
        double gd = 255.0 - g;
        double bd = 255.0 - b;

        r += (rd * p) / 100.0;
        g += (gd * p) / 100.0;
        b += (bd * p) / 100.0;
        return createColor((int) r, (int) g, (int) b);
    }

    public static Color darker(Color c, double p) {
        if (c == null) {
            return null;
        }

        double r = c.getRed();
        double g = c.getGreen();
        double b = c.getBlue();

        r -= (r * p) / 100.0;
        g -= (g * p) / 100.0;
        b -= (b * p) / 100.0;

        return createColor((int) r, (int) g, (int) b);
    }

    public static Color median(Color c1, Color c2) {
        if ((c1 == null || c2 == null)) {
            return null;
        }

        int r = (c1.getRed() + c2.getRed()) / 2;
        int g = (c1.getGreen() + c2.getGreen()) / 2;
        int b = (c1.getBlue() + c2.getBlue()) / 2;
        return createColor(r, g, b);
    }

    public static int getGrayValue(Color c) {
        if (c == null) {
            return 0;
        }

        double r = c.getRed();
        double g = c.getGreen();
        double b = c.getBlue();
        return Math.min(255, (int) (r * 0.28 + g * 0.59 + b * 0.13));
    }
    
    public static int getGrayValue(Color[] ca) {
        int sum = 0;
        for (int i = 0; i < ca.length; i++) {
            sum += getGrayValue(ca[i]);
        }
        return (sum / ca.length);
    }

    public static Color toGray(Color c) {
        if (c == null) {
            return null;
        }

        int gray = getGrayValue(c);
        return new Color(gray, gray, gray, c.getAlpha());
    }
}
