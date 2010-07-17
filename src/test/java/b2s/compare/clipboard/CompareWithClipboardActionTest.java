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

import org.netbeans.api.diff.DiffView;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class CompareWithClipboardActionTest {
    private SourceRetriever editorSourceRetriever;
    private SourceRetriever clipboardRetriever;
    private CompareWithClipboardAction action;
    private DialogDisplayer dialogDisplayer;
    private TextDiffer textDiffer;
    private DiffView diffView;

    @Before
    public void setUp() {
        editorSourceRetriever = mock(SourceRetriever.class);
        clipboardRetriever = mock(SourceRetriever.class);
        dialogDisplayer = mock(DialogDisplayer.class);
        textDiffer = mock(TextDiffer.class);
        diffView = mock(DiffView.class);

        action = new CompareWithClipboardAction(null);
        action.setEditorSourceRetriever(editorSourceRetriever);
        action.setClipboardSourceRetriever(clipboardRetriever);
        action.setDialogDisplayer(dialogDisplayer);
        action.setTextDiffer(textDiffer);

        when(editorSourceRetriever.retrieve()).thenReturn("editor");
        when(clipboardRetriever.retrieve()).thenReturn("clipboard");
        when(textDiffer.diff("editor", "clipboard")).thenReturn(diffView);
    }

    @Test
    public void clipboard_null() {
        when(clipboardRetriever.retrieve()).thenReturn(null);

        action.actionPerformed(null);

        verifyZeroInteractions(textDiffer, dialogDisplayer);
    }

    @Test
    public void clipboard_empty() {
        when(clipboardRetriever.retrieve()).thenReturn("");

        action.actionPerformed(null);

        verifyZeroInteractions(textDiffer, dialogDisplayer);
    }

    @Test
    public void nothingSelected_empty() {
        when(editorSourceRetriever.retrieve()).thenReturn("");

        action.actionPerformed(null);

        verifyZeroInteractions(textDiffer, dialogDisplayer);
    }

    @Test
    public void nothingSelected_null() {
        when(editorSourceRetriever.retrieve()).thenReturn(null);

        action.actionPerformed(null);

        verifyZeroInteractions(textDiffer, dialogDisplayer);
    }

    @Test
    public void hasDifference() {
        when(diffView.getDifferenceCount()).thenReturn(1);

        action.actionPerformed(null);

        verify(dialogDisplayer).showDifferences(diffView);
    }

    @Test
    public void noDifferences() {
        when(editorSourceRetriever.retrieve()).thenReturn("text");
        when(clipboardRetriever.retrieve()).thenReturn("text");

        action.actionPerformed(null);

        verify(dialogDisplayer).noDifferences();
    }
}