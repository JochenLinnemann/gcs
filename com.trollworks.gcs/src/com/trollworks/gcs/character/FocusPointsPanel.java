/*
 * Copyright ©1998-2020 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, version 2.0. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, version 2.0.
 */

package com.trollworks.gcs.character;

import com.trollworks.gcs.page.DropPanel;
import com.trollworks.gcs.page.PageField;
import com.trollworks.gcs.page.PageLabel;
import com.trollworks.gcs.page.PagePoints;
import com.trollworks.gcs.ui.layout.PrecisionLayout;
import com.trollworks.gcs.ui.layout.PrecisionLayoutAlignment;
import com.trollworks.gcs.ui.layout.PrecisionLayoutData;
import com.trollworks.gcs.utility.I18n;
import com.trollworks.gcs.utility.notification.NotifierTarget;

import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;

/** The character focus points panel. */
public class FocusPointsPanel extends DropPanel  {
    private static final Color          CURRENT_THRESHOLD_COLOR = new Color(255, 224, 224);
    private              CharacterSheet mSheet;

    /**
     * Creates a new focus points panel.
     *
     * @param sheet The sheet to display the data for.
     */
    public FocusPointsPanel(CharacterSheet sheet) {
        super(new PrecisionLayout().setColumns(3).setMargins(0).setSpacing(2, 0).setAlignment(PrecisionLayoutAlignment.FILL, PrecisionLayoutAlignment.FILL), I18n.Text("Focus Points"));
        mSheet = sheet;
        addLabelAndField(sheet, GURPSCharacter.ID_CURRENT_FOCUS, I18n.Text("Current"), I18n.Text("Current focus points"), true);
        addLabelAndField(sheet, GURPSCharacter.ID_FOCUS_POINTS, I18n.Text("Basic"), I18n.Text("Normal (i.e. fully rested) focus points"), true);
    }

    private PageField addLabelAndField(CharacterSheet sheet, String key, String title, String tooltip, boolean enabled) {
        add(new PagePoints(sheet, key), new PrecisionLayoutData().setHorizontalAlignment(PrecisionLayoutAlignment.END));
        PageField field = new PageField(sheet, key, SwingConstants.RIGHT, enabled, tooltip);
        add(field, new PrecisionLayoutData().setGrabHorizontalSpace(true).setHorizontalAlignment(PrecisionLayoutAlignment.FILL));
        add(new PageLabel(title, field));
        return field;
    }
}
