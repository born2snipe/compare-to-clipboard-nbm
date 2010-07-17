/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package b2s.compare.clipboard;

import java.awt.Dialog;
import java.awt.Frame;
import org.netbeans.api.diff.DiffView;
import org.openide.DialogDescriptor;
import org.openide.windows.WindowManager;

public class DialogDisplayer {
    public void noDifferences() {
        DialogDescriptor descriptor = new DialogDescriptor(
                "Contents are identical",
                "Compare to Clipboard",
                true,
                new Object[]{DialogDescriptor.OK_OPTION},
                DialogDescriptor.OK_OPTION,
                DialogDescriptor.DEFAULT_ALIGN,
                null,
                null
        );
        org.openide.DialogDisplayer.getDefault().notify(descriptor);
    }

    public void showDifferences(DiffView diffView) {
        DialogDescriptor descriptor = new DialogDescriptor(
                diffView.getComponent(),
                "Compare to Clipboard",
                true,
                new Object[]{DialogDescriptor.OK_OPTION},
                DialogDescriptor.OK_OPTION,
                DialogDescriptor.DEFAULT_ALIGN,
                null,
                null
        );
        Frame window = WindowManager.getDefault().getMainWindow();

        Dialog dialog = org.openide.DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.setSize(640, 480);
        dialog.setLocationRelativeTo(window);
        dialog.setVisible(true);
    }
}