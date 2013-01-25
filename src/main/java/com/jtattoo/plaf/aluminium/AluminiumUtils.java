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
 
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * @author  Michael Hagen
 */
public class AluminiumUtils {

    private AluminiumUtils() {
    }

    public static void fillComponent(Graphics g, Component c) {
        if (!JTattooUtilities.isMac() && AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
            Point offset = JTattooUtilities.getRelLocation(c);
            Dimension size = JTattooUtilities.getFrameSize(c);
            Graphics2D g2D = (Graphics2D) g;
            g2D.setPaint(new AluminiumGradientPaint(offset, size));
            g2D.fillRect(0, 0, c.getWidth(), c.getHeight());
            g2D.setPaint(null);
        } else {
            g.setColor(c.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
    }

    public static void fillComponent(Graphics g, Component c, int x, int y, int w, int h) {
        Graphics2D g2D = (Graphics2D) g;
        Shape savedClip = g2D.getClip();
        Area clipArea = new Area(new Rectangle2D.Double(x, y, w, h));
        clipArea.intersect(new Area(savedClip));
        g2D.setClip(clipArea);
        fillComponent(g, c);
        g2D.setClip(savedClip);
    }
}
