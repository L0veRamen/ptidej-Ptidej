package net.intensicode.idea.config;

import java.util.List;
import javax.swing.Icon;
import net.intensicode.idea.syntax.RecognizedToken;

/** TODO: Describe this! */
public interface InstanceConfiguration {
  Icon getIcon();

  String getName();

  String getDescription();

  String getExampleCode();

  boolean isVisibleToken(String aTokenId);

  List<RecognizedToken> getRecognizedTokens();

  String getTokenAttributes(String aTokenID);

  String getTokenDescription(String aTokenID);

  BracesConfiguration getBracesConfiguration();

  CommentConfiguration getCommentConfiguration();

  FileTypeConfiguration getFileTypeConfiguration();
}
