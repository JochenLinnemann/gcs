/*
 * Copyright (c) 1998-2020 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, version 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as defined by the
 * Mozilla Public License, version 2.0.
 */

package com.trollworks.gcs.modifier;

import com.trollworks.gcs.common.DataFile;
import com.trollworks.gcs.common.LoadState;
import com.trollworks.gcs.widgets.outline.ListRow;
import com.trollworks.gcs.widgets.outline.RowEditor;
import com.trollworks.toolkit.io.xml.XMLReader;
import com.trollworks.toolkit.io.xml.XMLWriter;
import com.trollworks.toolkit.ui.widget.outline.Column;
import com.trollworks.toolkit.utility.notification.Notifier;
import com.trollworks.toolkit.utility.text.Enums;
import com.trollworks.toolkit.utility.text.Numbers;

import java.io.IOException;

public class EquipmentModifier extends Modifier {
    private static final int                       CURRENT_VERSION     = 1;
    /** The root tag. */
    public static final  String                    TAG_MODIFIER        = "eqp_modifier";
    /** The attribute for the cost. */
    public static final  String                    TAG_COST            = "cost";
    /** The attribute for the cost type. */
    public static final  String                    ATTRIBUTE_COST_TYPE = "cost_type";
    /** The notification prefix used. */
    public static final  String                    NOTIFICATION_PREFIX = "eqpmod" + Notifier.SEPARATOR;
    /** The notification ID for enabled changes. */
    public static final  String                    ID_ENABLED          = NOTIFICATION_PREFIX + ATTRIBUTE_ENABLED;
    /** The notification ID for list changes. */
    public static final  String                    ID_LIST_CHANGED     = NOTIFICATION_PREFIX + "list_changed";
    /** The notification ID for cost changes. */
    public static final  String                    ID_COST             = NOTIFICATION_PREFIX + TAG_COST;
    private              EquipmentModifierCostType mCostType;
    private              double                    mCostAmount;

    /**
     * Creates a new {@link EquipmentModifier}.
     *
     * @param file  The {@link DataFile} to use.
     * @param other Another {@link EquipmentModifier} to clone.
     */
    public EquipmentModifier(DataFile file, EquipmentModifier other) {
        super(file, other);
        mCostType = other.mCostType;
        mCostAmount = other.mCostAmount;
    }

    /**
     * Creates a new {@link EquipmentModifier}.
     *
     * @param file   The {@link DataFile} to use.
     * @param reader The {@link XMLReader} to use.
     * @param state  The {@link LoadState} to use.
     */
    public EquipmentModifier(DataFile file, XMLReader reader, LoadState state) throws IOException {
        super(file, reader, state);
    }

    /**
     * Creates a new {@link EquipmentModifier}.
     *
     * @param file The {@link DataFile} to use.
     */
    public EquipmentModifier(DataFile file) {
        super(file);
        mCostType = EquipmentModifierCostType.COST_FACTOR;
        mCostAmount = 1;
    }

    @Override
    public EquipmentModifier cloneModifier() {
        return new EquipmentModifier(mDataFile, this);
    }

    @Override
    public String getNotificationPrefix() {
        return NOTIFICATION_PREFIX;
    }

    @Override
    public boolean isEquivalentTo(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof EquipmentModifier && super.isEquivalentTo(obj)) {
            EquipmentModifier row = (EquipmentModifier) obj;
            return mCostType == row.mCostType && mCostAmount == row.mCostAmount;
        }
        return false;
    }

    @Override
    public RowEditor<? extends ListRow> createEditor() {
        return new EquipmentModifierEditor(this);
    }

    public EquipmentModifierCostType getCostType() {
        return mCostType;
    }

    public boolean setCostType(EquipmentModifierCostType costType) {
        if (costType != mCostType) {
            mCostType = costType;
            notifySingle(ID_COST);
            return true;
        }
        return false;
    }

    public double getCostAmount() {
        return mCostAmount;
    }

    public boolean setCostAmount(double costAmount) {
        if (costAmount != mCostAmount) {
            mCostAmount = costAmount;
            notifySingle(ID_COST);
            return true;
        }
        return false;
    }

    @Override
    public String getXMLTagName() {
        return TAG_MODIFIER;
    }

    @Override
    public int getXMLTagVersion() {
        return CURRENT_VERSION;
    }

    @Override
    public Object getData(Column column) {
        return EquipmentModifierColumnID.values()[column.getID()].getData(this);
    }

    @Override
    public String getDataAsText(Column column) {
        return EquipmentModifierColumnID.values()[column.getID()].getDataAsText(this);
    }

    @Override
    protected void prepareForLoad(LoadState state) {
        super.prepareForLoad(state);
        mCostType = EquipmentModifierCostType.COST_FACTOR;
        mCostAmount = 1;
    }

    @Override
    protected void loadSubElement(XMLReader reader, LoadState state) throws IOException {
        String name = reader.getName();
        if (TAG_COST.equals(name)) {
            mCostType = Enums.extract(reader.getAttribute(ATTRIBUTE_COST_TYPE), EquipmentModifierCostType.values(), EquipmentModifierCostType.COST_FACTOR);
            mCostAmount = reader.readDouble(1);
        } else {
            super.loadSubElement(reader, state);
        }
    }

    @Override
    protected void saveSelf(XMLWriter out, boolean forUndo) {
        super.saveSelf(out, forUndo);
        out.simpleTagWithAttribute(TAG_COST, mCostAmount, ATTRIBUTE_COST_TYPE, Enums.toId(mCostType));
    }

    /** @return A full description of this {@link EquipmentModifier}. */
    public String getFullDescription() {
        StringBuilder builder = new StringBuilder();
        String        modNote = getNotes();
        builder.append(toString());
        if (!modNote.isEmpty()) {
            builder.append(" (");
            builder.append(modNote);
            builder.append(')');
        }
        return builder.toString();
    }

    /** @return The formatted cost. */
    public String getCostDescription() {
        StringBuilder builder = new StringBuilder();
        builder.append(mCostType.isMultiplier() ? Numbers.format(mCostAmount) : Numbers.formatWithForcedSign(mCostAmount));
        builder.append(' ');
        builder.append(mCostType);
        return builder.toString();
    }
}