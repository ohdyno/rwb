package ch6.template;

import java.io.PrintStream;

public class Template {

  public void applyTemplate(final String sourceTemplate, final String reqId, final PrintStream out) {
    final String code;
    final String altcode;

    try {
      String template = new String(sourceTemplate);

      // Substitute for %CODE%
      int templateSplitBegin = template.indexOf("%CODE%");
      int templateSplitEnd = templateSplitBegin + 6;
      String templatePartOne = new String(template.substring(0, templateSplitBegin));
      String templatePartTwo = new String(template.substring(templateSplitEnd,
          template.length()));
      code = new String(reqId);
      template = new String(templatePartOne + code + templatePartTwo);

      // Substitute for %ALTCODE%
      templateSplitBegin = template.indexOf("%ALTCODE%");
      templateSplitEnd = templateSplitBegin + 9;
      templatePartOne = new String(template.substring(0, templateSplitBegin));
      templatePartTwo = new String(template.substring(templateSplitEnd, template.length()));
      altcode = code.substring(0, 5) + "-" + code.substring(5, 8);
      out.print(templatePartOne + altcode + templatePartTwo);
    } catch (final Exception e) {
      System.out.println("Error in substitute()");
    }
  }
}
