/*
 * Copyright (c) 1998-2016 by Richard A. Wilkes. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * version 2.0. If a copy of the MPL was not distributed with this file, You
 * can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as defined
 * by the Mozilla Public License, version 2.0.
 */

package com.trollworks.gcs.common;

import com.trollworks.gcs.services.NotImplementedException;

import java.io.IOException;
import java.net.MalformedURLException;

/** Common functionality for GURPS Calculator Exports */
public interface GurpsCalculatorExportable {
	public boolean exportToGurpsCalculator() throws MalformedURLException, IOException, NotImplementedException;
}
