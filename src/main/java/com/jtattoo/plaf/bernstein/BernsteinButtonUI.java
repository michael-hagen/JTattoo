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
 
package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

/**
 * @author Michael Hagen
 */
public class BernsteinButtonUI extends BaseButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new BernsteinButtonUI();
    }

    protected void paintBackground(Graphics g, AbstractButton b) {
        if (b.isContentAreaFilled() && !(b.getParent() instanceof JMenuBar)) {
            int width = b.getWidth();
            int height = b.getHeight();
            Color colors[] = null;
            ButtonModel model = b.getModel();
            if (b.isEnabled()) {
                if (b.getBackground() instanceof ColorUIResource) {
                    if (model.isPressed() && model.isArmed()) {
                        colors = AbstractLookAndFeel.getTheme().getPressedColors();
                    } else if (b.isRolloverEnabled() && model.isRollover()) {
                        colors = AbstractLookAndFeel.getTheme().getRolloverColors();
                    } else {
                        if (JTattooUtilities.isFrameActive(b)) {
                            if (b.equals(b.getRootPane().getDefaultButton())) {
                                colors = AbstractLookAndFeel.getTheme().getSelectedColors();
                            } else {
                                colors = AbstractLookAndFeel.getTheme().getButtonColors();
                            }
                        } else {
                            colors = AbstractLookAndFeel.getTheme().getInActiveColors();
                        }
                    }
                } else {
                    if (model.isPressed() && model.isArmed()) {
                        colors = ColorHelper.createColorArr(b.getBackground(), ColorHelper.darker(b.getBackground(), 50), 20);
                    } else {
                        if (b.isRolloverEnabled() && model.isRollover()) {
                            colors = ColorHelper.createColorArr(ColorHelper.brighter(b.getBackground(), 80), ColorHelper.brighter(b.getBackground(), 20), 20);
                        } else {
                            colors = ColorHelper.createColorArr(ColorHelper.brighter(b.getBackground(), 40), ColorHelper.darker(b.getBackground(), 20), 20);
                        }
                    }
                }
            } else {
                colors = AbstractLookAndFeel.getTheme().getDisabledColors();
            }
            JTattooUtilities.fillHorGradient(g, colors, 0, 0, width, height);
        }
    }
}


