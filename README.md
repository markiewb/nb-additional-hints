<hr>
<h1 style="color: #FF0000">Looking for maintainers, who want to take over the development!</h1>
<hr>

[![Build Status](https://travis-ci.org/markiewb/nb-additional-hints.svg?branch=master)](https://travis-ci.org/markiewb/nb-additional-hints)
[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=K4CMP92RZELE2)

Plugin page: <a href="http://plugins.netbeans.org/plugin/47589/">http://plugins.netbeans.org/plugin/47589/</a>

<h1>Additional Java hints for NetBeans IDE</h1>

<h2>Supported Hints:</h2>

<ul>
<li>"Replace '+' with 'MessageFormat.format()'"</li>
<li>"Replace '+' with 'new StringBuilder().append()'"</li>
<li>"Replace '+' with 'String.format()'"</li>
<li>"Join literals"</li>
<li>"Copy joined literals to clipboard"</li>
<li>"Split at linebreaks"</li>
<li>"Split at caret" (since 1.1)</li>
<li>"Convert to assertTrue/assertFalse" (since 1.1)</li>
<li>"Support transformation to BigDecimal constants" (since 1.1)</li>
<li>"Remove "public abstract" modifiers from method declarations within interfaces" (since 1.1)</li>
<li>"Remove public/abstract/final modifiers from field declarations within interfaces" (since 1.2)</li>
<li>"Change modifiers" (since 1.2)</li>
<li>"Convert char to string and back" (since 1.2)</li>
<li>"Convert number in literal to number and back" (since 1.2)</li>
<li>"Convert to StringUtils.isBlank()/StringUtils.isNotBlank()/StringUtils.isEmpty()" (since 1.2)</li>
<li>"Convert from if/else to ternary and back" (since 1.2)</li>
<li>"Invert ternary if/else" (since 1.2)</li>
<li>"Report methods that have class name" (since 1.2, no transformation)</li>
<li>"Add "this." to methods and variables" (since 1.3, disabled by default)</li>
<li>"Replace with Optional.isPresent()/Convert return null to return Optional.empty()" (since 1.5)</li>
<li>"Replace with null-assignment to Optional with Optional.empty()| (since 1.6)</li>
<li>"Convert return xxx to return Optional.ofNullable(xxx)/Optional.of(xxx)/Optional.empty()| (since 1.6)</li>
<li>"Convert to assertNull" (since 1.6)</li>
<li>"Replace with org.junit.Assert" (since 1.6)</li>
</ul>

<h2>Example:</h2>
<img src="https://raw.githubusercontent.com/markiewb/nb-additional-hints/v1.6.1/doc/screenshot.png"/>

<h2>Updates</h2>
<h3>1.6.1:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/78">Bugfix</a>]: Fixed: Hints for converting ternary to if and back are missing</li>
</ul>
<h3>1.6.0:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/55">New Fix</a>]: Replace with null-assignment to Optional with Optional.empty()</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/56">New Fix</a>]: Convert return xxx to return Optional.ofNullable(xxx)/Optional.of(xxx)/Optional.empty()</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/63">New Fix</a>]: Convert to assertNull</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/68">New Fix</a>]: Replace with org.junit.Assert</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/54">Updated Fix</a>]: "Replace +..." works for more expressions</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/57">Updated Fix</a>]: Fixed false positive result from "Convert to Optional.isEmpty()"</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/58">Updated Fix</a>]: Make "Change modifiers" hints non-intrusive</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/59">Updated Fix</a>]: Make "Change modifiers" hints work for constructors too</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/64">Updated Fix </a>]: java.lang.NullPointerException in "Report methods that have class name"</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/75">Updated Fix </a>]: java.lang.NullPointerException in ReturnForOptional</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/70">Removed Fix</a>]: "dead instanceof" hint has been removed</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/72">Task</a>]: Update dependencies to NB 8.1</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/76">Task</a>]: Reduce dependencies</li>
</ul>
<h3>1.5.0:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/42">Updated Fix</a>]: "Convert to if/else" now supports assignments to new variables</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/43">Updated Fix</a>]: "Invert ternary"/"Convert to ternary" now support conditions without brackets</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/3">Updated Fix</a>]: "Replace +..." is not proposed for erroneous conditions anymore</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/51">Updated Fix</a>]: "Replace +..." works for more expressions</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/50">Updated Fix</a>]: Remove false positive detected by "Detect dead instanceof"</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/56">New Fix</a>]: Replace with Optional.isPresent()/Convert return null to return Optional.empty()</li>
<li>[Task]: Update requirements JDK7 and NB7.4</li>
</ul>
<h3>1.4.0:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/44">New Hint</a>]: Detect dead instanceof-expressions</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/2">Updated Fix</a>]: "Replace +..." hints can now supports chars too</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/47">Meta</a>]: Add donation link (for those who want to support this project)</li>
</ul>
<h3>1.3.0:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/36">Updated Fix</a>]: "Invert ternary if/else" now supports more patterns</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/39">New Hint</a>]: Add "this." to methods and variables (disabled by default)</li>
</ul>

<h3>1.2.0:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/pull/22">New Fix</a>]: Change the modifier of a class/method/field to public/package protected/protected/private  (by <a href="https://github.com/rasa-silva">rasa-silva</a>)</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/24">New Fix</a>]: Remove public/abstract/final modifiers from field declarations within interfaces</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/9">New Fix</a>]: Convert from char and string and back</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/10">New Fix</a>]: Convert number in literal to number and back</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/12">New Fix</a>]: Convert to StringUtils.isBlank()/StringUtils.isNotBlank()/StringUtils.isEmpty()</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/31">New Fix</a>]: Convert from if/else to ternary and back</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/32">New Fix</a>]: Invert ternary if/else</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/34">New Hint</a>]: Report methods that have class name</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/29">Updated Fix</a>]: "Convert to assertTrue/assertFalse" now supports junit.framework.Assert too</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/20">Updated Fix</a>]: "Replace +..." hints can now be configured</li>
</ul>

<h3>1.1.0:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/11">New Fix</a>]: Support transformation to BigDecimal constants</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/5">New Fix</a>]: Split a string at caret</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/13">New Fix</a>]: Convert to assertTrue/assertFalse</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/14">New Fix</a>]: Remove "public abstract" modifiers from method declarations within interfaces</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/8">Bugfix</a>]: Literals with quoted Strings won't be copied properly to clipboard</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/7">Refactoring</a>]: Convert to maven based module</li>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/18">Refactoring</a>]: Hints should be proposals instead of errors/warnings</li>
</ul>
<h3>1.0.x:</h3>
<ul>
<li>[<a href="https://github.com/markiewb/nb-additional-hints/issues/1">Bugfix</a>]: Literals with quoted Strings create uncompileable code</li>
</ul>
<h2>
<a name="further-information" class="anchor" href="#further-information"><span class="mini-icon mini-icon-link"></span></a>Further information:</h2>

<p>This plugin is orginally based on code from the <em>"I18N Checker"</em> plugin from <em>Jan Lahoda</em>.
The original sourcecode can be found at <a href="http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n">http://hg.netbeans.org/main/contrib/file/tip/editor.hints.i18n</a></p>

<p>License remains CDDL-GPL-2-CP - <a href="http://www.netbeans.org/cddl-gplv2.html">http://www.netbeans.org/cddl-gplv2.html</a></p>

<p>
Provide defects, request for enhancements and feedback at <a href=https://github.com/markiewb/nb-additional-hints/issues">https://github.com/markiewb/nb-additional-hints/issues</a>
</p>
<p>Compatible to NetBeans 8.1+</p>
<p>
<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=K4CMP92RZELE2"><img src="https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif" alt="btn_donate_SM.gif"></a>

</p>
