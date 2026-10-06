package net.intensicode.idea.config;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import java.util.List;
import net.intensicode.idea.syntax.RecognizedToken;

/** TODO: Describe this! */
public interface LanguageConfiguration {
  IElementType getToken(String aTokenId);

  SyntaxHighlighter getSyntaxHighlighter();

  List<RecognizedToken> getRecognizedTokens();

  TextAttributesKey getTextAttributesKey(String aTokenId);

  TextAttributesKey[] getTokenHighlights(IElementType tokenType);
}
