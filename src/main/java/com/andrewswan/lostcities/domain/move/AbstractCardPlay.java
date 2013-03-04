/**
 * Copyright (c) Andrew Swan 2008
 */
package com.andrewswan.lostcities.domain.move;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.util.Assert;

import com.andrewswan.lostcities.domain.Card;

/**
 * Convenient superclass for {@link CardPlay} implementations
 * 
 * @author Andrew
 */
public abstract class AbstractCardPlay implements CardPlay {

	// Properties
	protected final Card card;

	/**
	 * Constructor
	 * 
	 * @param card the played card; can't be <code>null</code>
	 */
	protected AbstractCardPlay(final Card card) {
		Assert.notNull(card, "Card can't be null");
		this.card = card;
	}

	@Override
	public String toString() {
		// For debugging
		return ToStringBuilder.reflectionToString(
				this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
