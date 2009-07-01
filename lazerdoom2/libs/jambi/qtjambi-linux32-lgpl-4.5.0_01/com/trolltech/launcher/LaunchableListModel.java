/****************************************************************************
 **
 ** Copyright (C) 1992-2009 Nokia. All rights reserved.
 **
 ** This file is part of Qt Jambi.
 **
 ** Commercial Usage
Licensees holding valid Qt Commercial licenses may use this file in
accordance with the Qt Commercial License Agreement provided with the
Software or, alternatively, in accordance with the terms contained in
a written agreement between you and Nokia.

GNU Lesser General Public License Usage
Alternatively, this file may be used under the terms of the GNU Lesser
General Public License version 2.1 as published by the Free Software
Foundation and appearing in the file LICENSE.LGPL included in the
packaging of this file.  Please review the following information to
ensure the GNU Lesser General Public License version 2.1 requirements
will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.

In addition, as a special exception, Nokia gives you certain
additional rights. These rights are described in the Nokia Qt LGPL
Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
package.

GNU General Public License Usage
Alternatively, this file may be used under the terms of the GNU
General Public License version 3.0 as published by the Free Software
Foundation and appearing in the file LICENSE.GPL included in the
packaging of this file.  Please review the following information to
ensure the GNU General Public License version 3.0 requirements will be
met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please
contact the sales department at qt-sales@nokia.com.

 **
 ** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 ** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 **
 ****************************************************************************/

package com.trolltech.launcher;

import com.trolltech.qt.core.*;

import java.util.*;

class LaunchableListModel extends QAbstractListModel {
    private List<Launchable> m_list = new ArrayList<Launchable>();

    public void add(Launchable l) {
        m_list.add(l);
        dataChanged.emit(createIndex(0, 0), createIndex(size() - 1, 0));
    }

    public int size() {
        return m_list.size();
    }

    public Launchable at(int i) {
        return m_list.get(i);
    }

    public Launchable at(QModelIndex index) {
        if (index == null)
            throw new ArrayIndexOutOfBoundsException("invalid index");
        return at(index.row());
    }

    // item view classes...
    @Override
    public int rowCount(QModelIndex parent) {
        return size();
    }

    @Override
    public Object data(QModelIndex index, int role) {
        if (role == Qt.ItemDataRole.DisplayRole)
            return at(index.row()).name();
        return null;
    }
};