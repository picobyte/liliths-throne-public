package com.lilithsthrone.game.character.body;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.BodyPartTypeInterface;
import com.lilithsthrone.game.character.body.types.NippleType;
import com.lilithsthrone.game.character.body.valueEnums.AreolaeShape;
import com.lilithsthrone.game.character.body.valueEnums.AreolaeSize;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.NippleShape;
import com.lilithsthrone.game.character.body.valueEnums.NippleSize;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.game.sex.Sex;
import com.lilithsthrone.main.Main;

/**
 * @since 0.1.83
 * @version 0.1.83
 * @author Innoxia
 */
public class Nipples extends AbstractOrifice implements BodyPartInterface, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected NippleType type;
	protected NippleShape nippleShape;
	protected AreolaeShape areolaeShape;
	protected int areolaeSize;
	protected int nippleSize;
	protected boolean pierced;

	public Nipples(NippleType type, int nippleSize, NippleShape nippleShape, int areolaeSize, int wetness, float capacity, int elasticity, int plasticity, boolean virgin) {
		super(wetness, capacity, elasticity, plasticity, virgin, type.getDefaultRacialOrificeModifiers());
		this.type = type;
		this.nippleSize = nippleSize;
		this.nippleShape = nippleShape;
		areolaeShape = AreolaeShape.NORMAL;
		this.areolaeSize = areolaeSize;
	}
	
	@Override
	public String getDeterminer(GameCharacter gc) {
		return type.getDeterminer(gc);
	}

	@Override
	public String getName(GameCharacter owner) {
		return getNamePlural(owner);
	}

	@Override
	public String getNameSingular(GameCharacter owner) {
		String name = "";
		
		switch(nippleShape) {
			case LIPS:
				name = UtilText.returnStringAtRandom("lipple", "nipple-lip");
				break;
			case NORMAL:
				name = type.getNameSingular(owner);
				break;
			case VAGINA:
				name = UtilText.returnStringAtRandom("nipple-cunt", "nipple-pussy");
				break;
		}
		
		return name;
	}

	@Override
	public String getNamePlural(GameCharacter owner) {
		String name = "";
		
		switch(nippleShape) {
			case LIPS:
				name = UtilText.returnStringAtRandom("lipples", "nipple-lips");
				break;
			case NORMAL:
				name = type.getNamePlural(owner);
				break;
			case VAGINA:
				name = UtilText.returnStringAtRandom("nipple-cunts", "nipple-pussies");
				break;
		}
		
		return name;
	}

	@Override
	public String getDescriptor(GameCharacter owner) {
		List<String> descriptorList = new ArrayList<>();
		
		for(OrificeModifier om : getOrificeModifiers()) {
			descriptorList.add(om.getName());
		}
		
		if(owner.isBreastFuckableNipplePenetration()) {
			switch(owner.getBreastMilkStorage().getAssociatedWetness()) {
				case ONE_SLIGHTLY_MOIST:
				case TWO_MOIST:
				case THREE_WET:
				case FOUR_SLIMY:
				case FIVE_SLOPPY:
				case SIX_SOPPING_WET:
				case SEVEN_DROOLING:
					descriptorList.add(owner.getBreastMilkStorage().getAssociatedWetness().getDescriptor());
					break;
				default:
					break;
			}
		}
		
		if(Main.game.isInSex() && Sex.getAllParticipants().contains(owner)) {
			if(Sex.hasLubricationTypeFromAnyone(owner, SexAreaOrifice.NIPPLE)) {
				descriptorList.add("wet");
			}
		}
		
		descriptorList.add(type.getDescriptor(owner));
		if(getCapacity()!= Capacity.ZERO_IMPENETRABLE) {
			descriptorList.add(getCapacity().getDescriptor());
		}
		
		return UtilText.returnStringAtRandom(descriptorList.toArray(new String[]{}));
	}

	@Override
	public NippleType getType() {
		return type;
	}

	public void setType(GameCharacter owner, BodyPartTypeInterface type) {
		this.type = (NippleType) type;
	}

	public NippleSize getNippleSize() {
		return NippleSize.getNippleSizeFromInt(nippleSize);
	}
	
	public int getNippleSizeValue() {
		return nippleSize;
	}

	public String setNippleSize(GameCharacter owner, int nippleSize) {
		int boundNippleSize = Math.max(0, Math.min(nippleSize, NippleSize.FOUR_MASSIVE.getValue()));
		if(this.nippleSize == boundNippleSize) {
			if(owner.isPlayer()) {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The size of your [pc.nipples] doesn't change...)]</p>");
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The size of [npc.namePos] [npc.nipples] doesn't change...)]</p>");
			}
		}
		
		String transformation = "";
		
		if(this.nippleSize > boundNippleSize) {
			if(owner.isPlayer()) {
				transformation = "<p>A soothing coolness rises up into your [pc.nipples], causing you to let out a surprised gasp as you feel them [style.boldShrink(shrinking)].<br/>";
			} else {
				transformation = UtilText.parse(owner, "<p>[npc.Name] lets out a little cry as [npc.she] feels a soothing coolness rise up into [npc.her] [npc.nipples], before they suddenly [style.boldShrink(shrink)].<br/>");
			}
			
		} else {
			if(owner.isPlayer()) {
				transformation = "<p>A pulsating warmth rises up into your [pc.nipples], causing you to let out a surprised gasp as you feel them [style.boldGrow(growing larger)].<br/>";
			} else {
				transformation = UtilText.parse(owner, "<p>[npc.Name] lets out a little cry as [npc.she] feels a pulsating warmth rise up into [npc.her] [npc.nipples], before they suddenly [style.boldGrow(grow larger)].<br/>");
			}
		}
		
		this.nippleSize = boundNippleSize;

		if(owner.isPlayer()) {
			return UtilText.parse(owner, transformation + "You now have [style.boldSex([pc.nippleSize] [pc.nipples])]!</p>");
		} else {
			return transformation
					+ UtilText.parse(owner, "[npc.Name] now has [style.boldSex([npc.nippleSize] [npc.nipples])]!</p>");
		}
	}

	public NippleShape getNippleShape() {
		return nippleShape;
	}
	
	public String setNippleShape(GameCharacter owner, NippleShape nippleShape) {
		
		if(this.nippleShape == nippleShape) {
			if(owner.isPlayer()) {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The shape of your [pc.nipples] doesn't change...)]</p>");
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The shape of [npc.namePos] [npc.nipples] doesn't change...)]</p>");
			}
		}
		
		String transformation = "";
		
		switch(nippleShape) {
			case NORMAL:
				if(owner.isPlayer()) {
					transformation = "<p>"
										+ "Your [pc.nipples] suddenly grow sore and sensitive, and before you have any time to react, they suddenly transform into normal-looking nipples.<br/>"
										+ "Your [pc.nipplesFullDescriptionColour] [pc.nipples] have transformed into [style.boldSex(normal nipples)]!"
									+ "</p>";
				} else {
					transformation = "<p>"
										+ "[npc.Name] shifts about uncomfortably as [npc.her] [npc.nipples] start to grow sore and sensitive, before suddenly transforming into normal-looking nipples.<br/>"
										+ "[npc.NamePos] [npc.nipplesFullDescriptionColour] [npc.nipples] have transformed into [style.boldSex(normal nipples)]!"
									+ "</p>";
				}
				break;
			case LIPS:
				if(owner.isPlayer()) {
					transformation = "<p>"
										+ "Your [pc.nipples] suddenly grow sore and sensitive, and before you have any time to react, they suddenly swell up and transform into juicy pairs of lips!<br/>"
										+ "Your [pc.nipplesFullDescriptionColour] [pc.nipples] have transformed into [style.boldSex(lip-like lipples)], which you can control just like regular lips!"
									+ "</p>";
				} else {
					transformation = "<p>"
										+ "[npc.Name] shifts about uncomfortably as [npc.her] [npc.nipples] start to grow sore and sensitive, before suddenly swelling up and transforming into juicy pairs of lips!<br/>"
										+ "[npc.NamePos] [npc.nipplesFullDescriptionColour] [npc.nipples] have transformed into [style.boldSex(lip-like lipples)], which [npc.she] can control just like regular lips!"
									+ "</p>";
				}
				break;
			case VAGINA:
				if(owner.isPlayer()) {
					transformation = "<p>"
										+ "Your [pc.nipples] suddenly grow sore and sensitive, and before you have any time to react, they suddenly shift and transform into vaginas!<br/>"
										+ "Your [pc.nipplesFullDescriptionColour] [pc.nipples] have transformed into [style.boldSex(vagina-like nipple-cunts)]!"
									+ "</p>";
				} else {
					transformation = "<p>"
										+ "[npc.Name] shifts about uncomfortably as [npc.her] [npc.nipples] start to grow sore and sensitive, before suddenly shifting and transforming into vaginas!<br/>"
										+ "[npc.NamePos] [npc.nipplesFullDescriptionColour] [npc.nipples] have transformed into [style.boldSex(vagina-like nipple-cunts)]!"
									+ "</p>";
				}
				break;
		}
		
		// Parse TF before changing nipple type:
		transformation = UtilText.parse(owner, transformation);
		
		this.nippleShape = nippleShape;
		
		return transformation;
	}
	
	public AreolaeShape getAreolaeShape() {
		return areolaeShape;
	}
	
	public String setAreolaeShape(GameCharacter owner, AreolaeShape areolaeShape) {
		
		if(this.areolaeShape == areolaeShape) {
			if(owner.isPlayer()) {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The shape of your areolae doesn't change...)]</p>");
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The shape of [npc.namePos] areolae doesn't change...)]</p>");
			}
		}

		this.areolaeShape = areolaeShape;
		
		String transformation = "";
		switch(areolaeShape) {
			case NORMAL:
				if(owner.isPlayer()) {
					transformation = "<p>Your [pc.nipples] suddenly start to tingle, and you gasp as you feel your areolae shift and transform into regular-looking circles.<br/>"
								+ "Your areolae are now shaped like [style.boldSex(circles)]!";
				} else {
					transformation = "<p>[npc.NamePos] [npc.nipples] suddenly start to tingle, and [npc.she] gasps as [npc.she] feels [npc.her] areolae shift and transform into regular-looking circles.<br/>"
							+ "[npc.NamePos] areolae are now shaped like [style.boldSex(circles)]!";
				}
				break;
			case HEART:
				if(owner.isPlayer()) {
					transformation = "<p>Your [pc.nipples] suddenly start to tingle, and you gasp as you feel your areolae shift and transform into the shape of hearts.<br/>"
							+ "Your areolae are now shaped like [style.boldSex(hearts)]!";
				} else {
					transformation = "<p>[npc.NamePos] [npc.nipples] suddenly start to tingle, and [npc.she] gasps as [npc.she] feels [npc.her] areolae shift and transform into the shape of hearts.<br/>"
							+ "[npc.NamePos] areolae are now shaped like [style.boldSex(hearts)]!";
				}
				break;
			case STAR:
				if(owner.isPlayer()) {
					transformation = "<p>Your [pc.nipples] suddenly start to tingle, and you gasp as you feel your areolae shift and transform into the shape of stars.<br/>"
							+ "Your areolae are now shaped like [style.boldSex(stars)]!";
				} else {
					transformation = "<p>[npc.NamePos] [npc.nipples] suddenly start to tingle, and [npc.she] gasps as [npc.she] feels [npc.her] areolae shift and transform into the shape of stars.<br/>"
							+ "[npc.NamePos] areolae are now shaped like [style.boldSex(stars)]!";
				}
				break;
		}
		
		return UtilText.parse(owner, transformation);
	}

	public AreolaeSize getAreolaeSize() {
		return AreolaeSize.getAreolaeSizeFromInt(areolaeSize);
	}
	
	public int getAreolaeSizeValue() {
		return areolaeSize;
	}

	public String setAreolaeSize(GameCharacter owner, int areolaeSize) {
		int boundAreolaeSize = Math.max(0, Math.min(areolaeSize, AreolaeSize.FOUR_MASSIVE.getValue()));
		if (this.areolaeSize == boundAreolaeSize) {
			if(owner.isPlayer()) {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The size of your areolae doesn't change...)]</p>");
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The size of [npc.namePos] areolae doesn't change...)]</p>");
			}
		}
		
		String transformation = "";
		
		if (this.areolaeSize > boundAreolaeSize) {
			if(owner.isPlayer()) {
				transformation = "<p>You feel a strange tingling sensation suddenly build up around your [pc.nipples], and you let out a little cry as you feel your areolae [style.boldShrink(shrinking)].<br/>";
			} else {
				transformation = UtilText.parse(owner, "<p>[npc.Name] lets out a little cry as [npc.she] feels a strange tingling sensation suddenly build up around [npc.her] [npc.nipples], before [npc.her] areolae suddenly [style.boldShrink(shrink)].<br/>");
			}
			
		} else {
			if(owner.isPlayer()) {
				transformation = "<p>You feel a strange tingling sensation suddenly build up around your [pc.nipples], and you let out a little cry as you feel your areolae [style.boldGrow(getting larger)].<br/>";
			} else {
				transformation = UtilText.parse(owner, "<p>[npc.Name] lets out a little cry as [npc.she] feels a strange tingling sensation suddenly build up around [npc.her] [npc.nipples], before [npc.her] areolae suddenly [style.boldGrow(grow larger)].<br/>");
			}
		}
		
		this.areolaeSize = boundAreolaeSize;

		if(owner.isPlayer()) {
			return UtilText.parse(owner, transformation
				+ "You now have [style.boldSex([pc.areolaeSize] [pc.nipples])]!</p>");
		} else {
			return transformation
					+ UtilText.parse(owner, "[npc.Name] now has [style.boldSex([npc.areolaeSize] [npc.nipples])]!</p>");
		}
	}

	public boolean isPierced() {
		return pierced;
	}
	
	public String setPierced(GameCharacter owner, boolean pierced) {
		if(this.pierced == pierced) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		this.pierced = pierced;
		
		if(pierced) {
			if(owner.isPlayer()) {
				return "<p>Your [pc.nipples] are now [style.boldGrow(pierced)]!</p>";
			} else {
				return UtilText.parse(owner,
						"<p>[npc.NamePos] [npc.nipples] are now [style.boldGrow(pierced)]!</p>");
			}
			
		} else {
			AbstractClothing c = owner.getClothingInSlot(InventorySlot.PIERCING_NIPPLE);
			String piercingUnequip = "";
			if(c!=null) {
				owner.forceUnequipClothingIntoVoid(owner, c);
				piercingUnequip = owner.addClothing(c, false);
			}
			
			if(owner.isPlayer()) {
				return "<p>"
							+ "Your [pc.nipples] are [style.boldShrink(no longer pierced)]!"
						+ "</p>"
						+piercingUnequip;
			} else {
				return UtilText.parse(owner,
						"<p>"
								+ "[npc.NamePos] [npc.nipples] are [style.boldShrink(no longer pierced)]!"
						+ "</p>"
						+piercingUnequip);
			}
		}
		
	}

	@Override
	public String getWetnessChangeDescription(GameCharacter owner, int wetnessChange) {

		if(wetnessChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(The wetness of your [pc.nipples] doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The wetness of [npc.namePos] [npc.nipples] doesn't change...)]</p>");
		}

		String wetnessDescriptor = getWetness(owner).getDescriptor();
		if (wetnessChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "A sudden wetness at your chest gets your attention. It looks like a moisture keeps oozing out of your [pc.nipples]. Are they [style.boldGrow(lubricating)] themselves now?.<br/>"
				+ "The transformation then passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " nipples)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] investigates her chest as [npc.she] feels moisture oozing from [npc.her] [npc.nipples]. [npc.she] sighs as [npc.she] realises that [npc.her] [npc.nipples] are [style.boldGrow(lubricating)] themselves.<br/>"
					+ "The transformation then passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " nipples)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "The moisture suddenly [style.boldShrink(oozes less)] from your [pc.nipples].<br/>"
			+ "The transformation then passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " nipples)]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] notices [npc.her] [npc.nipples] [style.boldShrink(ooze less)].<br/>"
				+ "The transformation then passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " nipples)]!"
				+ "</p>");
	}
	
	@Override
	public String getCapacityChangeDescription(GameCharacter owner, float capacityChange) {
		if (capacityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(The capacity of your [pc.nipples] doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The capacity of [npc.namePos] [npc.nipples] doesn't change...)]</p>");
		}
		String capacityDescriptor = getCapacity().getDescriptor();
		if (capacityChange > 0) {
			if(capacity == capacityChange) { // Getting fuckable nipples:
				if (owner.isPlayer())
					return "<p>"
					+ "You squirm about uncomfortably as your [pc.nipples] grow unusually hard and sensitive."
					+ " A strange pressure starts to build up deep within your torso, and you let out a groan as you feel a drastic transformation taking place deep within your [pc.breasts]."
					+ " Your groan turns into [pc.a_moan+], which bursts out of your mouth as you feel your [pc.nipples] suddenly [style.boldGrow(spread open)], revealing a deep, fuckable passage that's formed behind them.<br/>"
					+ "You now have [style.boldSex(" + capacityDescriptor + ", fuckable [pc.nipples])]!"
					+ "</p>";

				return UtilText.parse(owner, "<p>" 
						+ "[npc.Name] squirms about uncomfortably as [npc.her] [npc.nipples] grow unusually hard and sensitive."
						+ " A strange pressure starts to build up deep within [npc.her] torso, and [npc.she] lets out a groan as a drastic transformation takes place deep within [npc.her] [npc.breasts]."
						+ " [npc.Her] groan turns into [npc.a_moan+], which bursts out of [npc.her] mouth as [npc.her] [npc.nipples] suddenly [style.boldGrow(spread open)], revealing a deep, fuckable passage that's formed behind them.<br/>"
						+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + ", fuckable [npc.nipples])]!"
						+ "</p>");

			} else { // Expanding fuckable nipples:
				if (owner.isPlayer())
					return "<p>"
					+ "You let out [pc.a_moan+] as you feel a familiar pressure building up behind your fuckable [pc.nipples], before they suddenly [style.boldGrow(grow)] both wider and deeper.<br/>"
					+ "You now have [style.boldSex(" + capacityDescriptor + " [pc.nipples])]!"
					+ "</p>";

				return UtilText.parse(owner, "<p>"
						+ "[npc.Name] lets out [npc.a_moan+] as [npc.she] feels a familiar pressure building up behind [npc.her] fuckable [npc.nipples], before they suddenly [style.boldGrow(grow)] both wider and deeper.<br/>"
						+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + " [npc.nipples])]!"
						+ "</p>");
			}
		}
		if(capacity == 0) { // Losing fuckable nipples:
			if (owner.isPlayer())
				return "<p>"
				+ "You squirm about uncomfortably as your [pc.nipples] grow unusually hard and sensitive."
				+ " A strange pressure starts to build up deep within your torso, and you let out a groan as you feel a drastic transformation taking place deep within your [pc.breasts]."
				+ " Your groan turns into a little sigh as you feel your [pc.nipples] suddenly [style.boldShrink(clench shut)], removing the ability for them to be fucked.<br/>"
				+ "You now have [style.boldSex(" + capacityDescriptor + ", non-fuckable [pc.nipples])]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>" 
					+ "[npc.Name] squirms about uncomfortably as [npc.her] [npc.nipples] grow unusually hard and sensitive."
					+ " A strange pressure starts to build up deep within [npc.her] torso, and [npc.she] lets out a groan as a drastic transformation takes place deep within [npc.her] [npc.breasts]."
					+ " [npc.Her] groan turns into a little sigh as [npc.her] [npc.nipples] suddenly [style.boldShrink(clench shut)], removing the ability for them to be fucked.<br/>"
					+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + ", non-fuckable [npc.nipples])]!"
					+ "</p>");

		} else { // Shrinking fuckable nipples:
			if (owner.isPlayer())
				return "<p>"
				+ "You let out [pc.a_moan+] as you feel a familiar pressure building up behind your fuckable [pc.nipples], before they suddenly [style.boldShrink(shrink)] and become tighter.<br/>"
				+ "You now have [style.boldSex(" + capacityDescriptor + " [pc.nipples])]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out [npc.a_moan+] as [npc.she] feels a familiar pressure building up behind [npc.her] fuckable [npc.nipples], before they suddenly [style.boldShrink(shrink)] and become tighter.<br/>"
					+ "[npc.Name] now has [style.boldSex(" + capacityDescriptor + " [npc.nipples])]!"
					+ "</p>");
		}
	}

	@Override
	public String getElasticityChangeDescription(GameCharacter owner, int elasticityChange) {
		if (elasticityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(The elasticity of your [pc.nipples] doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The elasticity of [npc.namePos] [npc.nipples] doesn't change...)]</p>");
		}
		String elasticityDescriptor = getElasticity().getDescriptor();
		if (elasticityChange > 0) {

			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange slackening sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldGrow(elasticity increases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [pc.nipples])]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange slackening sensation pulsating deep within [npc.her] [npc.breasts] as [npc.her] [npc.nipple] [style.boldGrow(elasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [npc.nipples])]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You let out a little gasp as you feel a strange clenching sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldShrink(elasticity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [pc.nipples])]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange clenching sensation pulsating deep within [npc.her] [npc.breasts] as [npc.her] [npc.nipple] [style.boldShrink(elasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " [npc.nipples])]!"
				+ "</p>");
	}

	@Override
	public String getPlasticityChangeDescription(GameCharacter owner, int plasticityChange) {
		if (plasticityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your [pc.nipples]'s plasticity doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The plasticity of [npc.namePos] [npc.nipples] doesn't change...)]</p>");
		}
		String plasticityDescriptor = getPlasticity().getDescriptor();
		if (plasticityChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange moulding sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldGrow(plasticity increases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [pc.nipples])]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
							+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange moulding sensation pulsating deep within [npc.her] [npc.breasts] as [npc.her] [npc.nipples]' [style.boldGrow(plasticity increases)].<br/>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [npc.nipples])]!"
							+ "</p>");		
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You let out a little gasp as you feel a strange softening sensation pulsating deep within your [pc.breasts] as your [pc.nipples]' [style.boldShrink(plasticity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [pc.nipples])]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
						+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange softening sensation pulsating deep within [npc.her] [npc.breasts] as [npc.her] [npc.nipples]' [style.boldShrink(plasticity decreases)].<br/>"
						+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " [npc.nipples])]!"
						+ "</p>");

	}

	@Override
	public String addOrificeModifier(GameCharacter owner, OrificeModifier modifier) {
		if(hasOrificeModifier(modifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		orificeModifiers.add(modifier);
		
		switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within your [pc.breasts], and you can't help but let out [pc.a_moan+] as you feel a series of [style.boldGrow(extra muscles)] growing down into the lining of your [pc.nipples]."
								+ " You're shocked to discover that you have an incredible amount of control over them, allowing you to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex(The interior of your [pc.nipples] are now lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within [npc.namePos] [npc.breasts], and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(extra muscles)]"
									+ " growing down into the lining of [npc.her] [npc.nipples]."
								+ " [npc.sheIs] shocked to discover that [npc.she] has an incredible amount of control over them, allowing [npc.her] to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] [npc.nipples] are now lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within your [pc.breasts], and you can't help but let out [pc.a_moan+] as you feel a series of [style.boldGrow(fleshy, highly-sensitive ribs)]"
									+ " growing down into the lining of your [pc.nipples]."
								+ " Shifting your [pc.breasts] around a little, a jolt of pleasure shoots through your torso as you feel your new additions rub over one another, making you wonder how good it would feel to have your [pc.nipples] fucked.<br/>"
								+ "[style.boldSex(The interior of your [pc.nipples] are now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within [npc.namePos] [npc.breasts], and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a series of [style.boldGrow(fleshy, highly-sensitive ribs)]"
									+ " growing down into the lining of [npc.her] [npc.nipples]."
								+ " Shifting [npc.her] [npc.breasts] around a little, a jolt of pleasure shoots through [npc.her] torso as [npc.she] feels [npc.her] new additions rub over one another, causing [npc.herHim] to let out another [npc.moan+].<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] [npc.nipples] are now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within your [pc.breasts], and you can't help but let out [pc.a_moan+] as you feel a strange tingling sensation deep down in your [pc.nipples]."
								+ " The tingling sensation grows stronger, and a surprised cry bursts out from your mouth as you suddenly discover that the insides of your [pc.nipples] are now filled with"
									+ " [style.boldGrow(a series of little wriggling tentacles)], over which you have limited control.<br/>"
								+ "[style.boldSex(The insides of your [pc.nipples] are now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "An intense pressure suddenly swells up deep within [npc.namePos] [npc.breasts], and [npc.she] can't help but let out [npc.a_moan+] as [npc.she] feels a strange tingling sensation deep down in [npc.her] [npc.nipples]."
								+ " The tingling sensation grows stronger, and a surprised cry bursts out from [npc.her] mouth as [npc.she] suddenly discovers that the insides of [npc.her] [npc.nipples] are now filled with"
										+ " [style.boldGrow(a series of little wriggling tentacles)], over which [npc.she] has limited control.<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] [npc.nipples] are now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your [pc.nipples], and you let out a little cry as you feel them swell out and [style.boldGrow(puff up)].<br/>"
								+ "[style.boldTfSex(Your [pc.nipples] are now extremely puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.nipples], before they suddenly swell out and [style.boldGrow(puff up)].<br/>"
								+ "[style.boldSex([npc.NamePos] [npc.nipples] are now extremely puffy!)]"
							+ "</p>";
				}
		}
		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}

	@Override
	public String removeOrificeModifier(GameCharacter owner, OrificeModifier modifier) {
		if(!hasOrificeModifier(modifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		orificeModifiers.remove(modifier);
		
		switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
								+ "A soothing warmth slowly washes up through your torso, and you can't help but let out a gentle [pc.moan] as you feel your [style.boldShrink(extra muscles)] melt back into the flesh of your [pc.breasts].<br/>"
								+ "[style.boldSex(The interior of your [pc.nipples] are no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "A soothing warmth slowly washes up through [npc.namePos] torso, and a gentle [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] feels [npc.her] [style.boldShrink(extra muscles)]"
									+ " melt back into the flesh of [npc.her] [npc.breasts].<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] [npc.nipples] are no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "A soothing warmth slowly washes up through your torso, and you can't help but let out a gentle [pc.moan] as you feel your [style.boldShrink(fleshy, highly-sensitive ribs)] melt back into the flesh of your [pc.breasts].<br/>"
								+ "[style.boldSex(The interior of your [pc.nipples] are no longer ribbed!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "A soothing warmth slowly washes up through [npc.namePos] torso, and a gentle [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] feels [npc.her] [style.boldShrink(fleshy, highly-sensitive ribs)]"
									+ " melt back into the flesh of [npc.her] [npc.breasts].<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] [npc.nipples] are no longer ribbed!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "A soothing warmth slowly washes up through your torso, and you can't help but let out a gentle [pc.moan] as you feel your [style.boldShrink(little wriggling tentacles)] melt back into the flesh of your [pc.breasts].<br/>"
								+ "[style.boldSex(The interior of your [pc.nipples] are no longer filled with little tentacles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "A soothing warmth slowly washes up through [npc.namePos] torso, and a gentle [pc.moan] drifts out from between [npc.her] [npc.lips] as [npc.she] feels [npc.her] [style.boldShrink(little wriggling tentacles)]"
									+ " melt back into the flesh of [npc.her] [npc.breasts].<br/>"
								+ "[style.boldSex(The interior of [npc.namePos] [npc.nipples] are no longer filled with little tentacles!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your [pc.nipples], and you let out a little sigh as you feel them [style.boldShrink(shrink down)], losing their puffiness.<br/>"
								+ "[style.boldTfSex(Your [pc.nipples] are no longer extremely puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little sigh as [npc.her] [npc.nipples] [style.boldShrink(shrink down)] and lose their puffiness.<br/>"
								+ "[style.boldSex([npc.NamePos] [npc.nipples] are no longer extremely puffy!)]"
							+ "</p>";
				}
		}
		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}
}