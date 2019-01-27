/**
 * The tests within this package focuses on testing the schema validation.
 * 
 * Next we will break down the situations covered within these tests.
 * <ul>
 * <li> &lt;commandSchema&gt;
 * 	 <ul>
 *     <li> Empty XML {@link CommandSchemaValidation#emptyFile()}</li>
 *     <li> &lt;commandSchema&gt; with no elements or attributes {@link CommandSchemaValidation#emptyElement()}</li>
 *     <li> &lt;commandSchema&gt; with two &lt;tokens&gt; {@link CommandSchemaValidation#twoTokens()}</li>
 *     <li> &lt;commandSchema&gt; with two &lt;failure&gt; {@link CommandSchemaValidation#twoFailures()}</li>
 *     <li> &lt;commandSchema&gt; with two &lt;nullToken&gt; {@link CommandSchemaValidation#twoNullTokens()}</li>
 *     <li> &lt;commandSchema&gt; with a foreign element before {@link CommandSchemaValidation#foreignElementBefore()}</li>
 *     <li> &lt;commandSchema&gt; with a foreign element middle {@link CommandSchemaValidation#foreignElementMiddle()}</li>
 *     <li> &lt;commandSchema&gt; with a foreign element end {@link CommandSchemaValidation#foreignElementEnd()}</li>
 *     <li> &lt;commandSchema&gt; with a foreign attribute {@link CommandSchemaValidation#foreignAttribute()}</li>
 *     <li> &lt;commandSchema&gt; with wrong attribute type {@link CommandSchemaValidation#wrongAttributeType()}</li>
 *     <li> Valid &lt;commandSchema&gt; with just &lt;tokens&gt; {@link CommandSchemaValidation#justTokens()}</li>
 *     <li> Valid &lt;commandSchema&gt; {@link CommandSchemaValidation#full()}</li>
 *   </ul>
 * </li> 
 * 
 * <li> &lt;tokens&gt
 *   <ul>
 *     <li> &lt;tokens&gt; with no elements or attributes {@link TokensValidation#emptyElement()}</li>
 *     <li> &lt;tokens&gt; with a foreign attribute {@link TokensValidation#foreignAttribute()}</li>
 *     <li> &lt;tokens&gt; with a foreign element before {@link TokensValidation#foreignElementBefore()}</li>
 *     <li> &lt;tokens&gt; with a foreign element middle {@link TokensValidation#foreignElementMiddle()}</li>
 *     <li> &lt;tokens&gt; with a foreign element end {@link TokensValidation#foreignElementEnd()}</li>
 *     <li> &lt;tokens&gt; with no elements {@link TokensValidation#emptyElement()}</li>
 *     <li> Valid &lt;tokens&gt; {@link TokensValidation#full()}</li>
 *   </ul>
 * </li>
 * 
 * <li> &lt;token&gt;
 * 	 <ul>
 *     <li> &lt;token&gt; with no elements or attributes {@link TokenValidation#emptyElement()}</li>
 *     <li> &lt;token&gt; with a foreign attribute {@link TokenValidation#foreignAttribute()}</li>
 *     <li> &lt;token&gt; with a foreign element before {@link TokenValidation#foreignElementBefore()}</li>
 *     <li> &lt;token&gt; with a foreign element middle {@link TokenValidation#foreignElementMiddle()}</li>
 *     <li> &lt;token&gt; with a foreign element end {@link TokenValidation#foreignElementEnd()}</li>
 *     <li> &lt;token&gt; with two &lt;actions&gt; {@link TokenValidation#twoActions()}</li>
 *     <li> &lt;token&gt; with two &lt;tokens&gt; {@link TokenValidation#twoTokens()}</li>
 *     <li> &lt;token&gt; with two &lt;response&gt; {@link TokenValidation#twoResponses()}</li>
 *     <li> &lt;token&gt; with two &lt;failure&gt; {@link TokenValidation#twoFailures()}</li>
 *     <li> &lt;token&gt; with wrong attribute type {@link CommandSchemaValidation#wrongAttributeType()}</li>
 *     <li> Valid &lt;token&gt; with just <i>value</i> {@link TokenValidation#justValueAttribute()}</li>
 *     <li> Valid &lt;token&gt; {@link TokenValidation#full()}</li>
 *   </ul
 * </li> 
 * 
 * <li> &lt;actions&gt;
 * 	 <ul>
 *     <li> &lt;actions&gt; with no elements or attributes {@link ActionsValidation#emptyElement()}</li>
 *     <li> &lt;actions&gt; with a foreign attribute {@link ActionsValidation#foreignAttribute()}</li>
 *     <li> &lt;actions&gt; with a foreign element before {@link ActionsValidation#foreignElementBefore()}</li>
 *     <li> &lt;actions&gt; with a foreign element middle {@link ActionsValidation#foreignElementMiddle()}</li>
 *     <li> &lt;actions&gt; with a foreign element end {@link ActionsValidation#foreignElementEnd()}</li>
 *     <li> &lt;actions&gt; with no elements {@link ActionsValidation#emptyElement()}</li>
 *     <li> Valid &lt;actions&gt; {@link ActionsValidation#full()}</li>
 *   </ul>
 * </li>
 * 
 * <li> &lt;action&gt;
 * 	 <ul>
 *     <li> &lt;action&gt; with no elements or attributes {@link ActionValidation#emptyElement()}</li>
 *     <li> &lt;action&gt; with a foreign attribute {@link ActionValidation#foreignAttribute()}</li>
 *     <li> &lt;action&gt; with a foreign element {@link ActionValidation#foreignElement()}</li>
 *     <li> Valid &lt;action&gt; with just <i>name</i> {@link ActionsValidation#justNameAttribute()}</li>
 *     <li> Valid &lt;action&gt; {@link ActionValidation#full()}</li>
 *   </ul>
 * </li> 
 *  
 * <li> &lt;failure&gt;
 * 	 <ul>
 *     <li> &lt;failure&gt; with no elements or attributes {@link FailureValidation#emptyElement()}</li>
 *     <li> &lt;failure&gt; with a foreign attribute {@link FailureValidation#foreignAttribute()}</li>
 *     <li> &lt;failure&gt; with a foreign element {@link FailureValidation#foreignElement()}</li>
 *     <li> Valid &lt;failure&gt; {@link FailureValidation#full()}</li>
 *   </ul>
 * </li> 
 *
 * <li> &lt;nullToken&gt;
 * 	 <ul>
 *     <li> &lt;nullToken&gt; with no elements or attributes {@link NullTokenValidation#emptyElement()}</li>
 *     <li> &lt;nullToken&gt; with a foreign attribute {@link NullTokenValidation#foreignAttribute()}</li>
 *     <li> &lt;nullToken&gt; with a foreign element {@link NullTokenValidation#foreignElement()}</li>
 *     <li> Valid &lt;nullToken&gt; {@link NullTokenValidation#full()}</li>
 *   </ul>
 * </li> 
 * 
 * <li> &lt;response&gt;
 * 	 <ul>
 *     <li> &lt;response&gt; with no elements or attributes {@link ResponseValidation#emptyElement()}</li>
 *     <li> &lt;response&gt; with a foreign attribute {@link ResponseValidation#foreignAttribute()}</li>
 *     <li> &lt;response&gt; with a foreign element {@link ResponseValidation#foreignElement()}</li>
 *     <li> Valid &lt;response&gt; {@link ResponseValidation#full()}</li>
 *   </ul>
 * </li> 
 * 
 * </ul>
 */
package com.rvida404.commandhandler.validation;

import com.rvida404.commandhandler.validation.action.ActionValidation;
import com.rvida404.commandhandler.validation.actions.ActionsValidation;
import com.rvida404.commandhandler.validation.commandschema.CommandSchemaValidation;
import com.rvida404.commandhandler.validation.failure.FailureValidation;
import com.rvida404.commandhandler.validation.nulltoken.NullTokenValidation;
import com.rvida404.commandhandler.validation.response.ResponseValidation;
import com.rvida404.commandhandler.validation.token.TokenValidation;
import com.rvida404.commandhandler.validation.tokens.TokensValidation;
