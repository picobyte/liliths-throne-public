package com.lilithsthrone.game.character.body;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.AnusType;
import com.lilithsthrone.game.character.body.valueEnums.BodyHair;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.body.valueEnums.Wetness;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.game.sex.Sex;
import com.lilithsthrone.main.Main;

/**
 * @since 0.1.83
 * @version 0.1.83
 * @author Innoxia
 */
public class Anus extends AbstractOrifice implements BodyPartInterface, Serializable {

	private static final long serialVersionUID = 1L;
	
	// Asshole variables:
	protected AnusType type;
	protected boolean bleached;
	protected BodyHair assHair;

	public Anus(AnusType type, int wetness, float capacity, int elasticity, int plasticity, boolean virgin) {
		super(wetness, capacity, elasticity, plasticity, virgin, type.getDefaultRacialOrificeModifiers());
		this.type = type;

		bleached = false;
		assHair = BodyHair.ZERO_NONE;
	}

	@Override
	public String getDeterminer(GameCharacter gc) {
		return type.getDeterminer(gc);
	}
	
	@Override
	public String getName(GameCharacter gc) {
		return type.getName(gc);
	}
	
	@Override
	public String getNameSingular(GameCharacter gc) {
		return type.getNameSingular(gc);
	}

	@Override
	public String getNamePlural(GameCharacter gc) {
		return type.getNamePlural(gc);
	}

	@Override
	public String getDescriptor(GameCharacter owner) {
		List<String> descriptorList = new ArrayList<String>();

		for(OrificeModifier om : getOrificeModifiers()) {
			descriptorList.add(om.getName());
		}

		String wetnessDescriptor = getWetness(owner).getDescriptor();
		if(Main.game.isInSex() && Sex.getAllParticipants().contains(owner)) {
			if(Sex.hasLubricationTypeFromAnyone(owner, SexAreaOrifice.ANUS)) {
				wetnessDescriptor = "wet";
			}
		}
		descriptorList.add(wetnessDescriptor);
		if((owner.getAssHair()==BodyHair.SIX_BUSHY || owner.getAssHair()==BodyHair.THREE_TRIMMED) && Main.game.isAssHairEnabled()) {
			descriptorList.add("hairy");
		}
		descriptorList.add(type.getDescriptor(owner));
		descriptorList.add(getCapacity().getDescriptor());

		return UtilText.returnStringAtRandom(descriptorList.toArray(new String[]{}));
	}

	@Override
	public AnusType getType() {
		return type;
	}
	
	public void setType(AnusType type) {
		this.type = type;
	}

	public boolean isBleached() {
		return bleached;
	}
	
	public String setAssBleached(GameCharacter owner, boolean bleached) {
		if(this.bleached == bleached) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		this.bleached = bleached;
		
		if(bleached) {
			if(owner.isPlayer()) {
				return "<p>[style.boldTfSex(Your asshole is now bleached!)]</p>";
			} else {
				return UtilText.parse(owner,
						"<p>[style.boldTfSex([npc.NamePos] asshole is now bleached!)]</p>");
			}
		} else {
			if(owner.isPlayer()) {
				return "<p>[style.boldTfSex(Your asshole is no longer bleached!)]</p>";
			} else {
				return UtilText.parse(owner,
						"<p>[style.boldTfSex([npc.NamePos] asshole is no longer bleached!)]</p>");
			}
		}
	}

	public BodyHair getAssHair() {
		return assHair;
	}
	
	public Covering getAssHairType(GameCharacter owner) {
		return owner.getCovering(owner.getBodyHairCoveringType(owner.getAssType().getRace()));
	}
	
	public String setAssHair(GameCharacter owner, BodyHair assHair) {
		if(owner==null) {
			this.assHair=assHair;
			return "";
		}
		String transformation = "";
		
		if(getAssHair() == assHair) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
			
		} else {
			if(owner.isPlayer()) {
				switch(assHair) {
					case ZERO_NONE:
						transformation = "<p>There is no longer any trace of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
					case ONE_STUBBLE:
						transformation = "<p>You now have a stubbly patch of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
					case TWO_MANICURED:
						transformation = "<p>You now have a well-manicured patch of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
					case THREE_TRIMMED:
						transformation = "<p>You now have a trimmed patch of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
					case FOUR_NATURAL:
						transformation = "<p>You now have a natural amount of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
					case FIVE_UNKEMPT:
						transformation = "<p>You now have an unkempt bush of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
					case SIX_BUSHY:
						transformation = "<p>You now have a thick, bushy mass of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
					case SEVEN_WILD:
						transformation = "<p>You now have a wild, bushy mass of "+getAssHairType(owner).getFullDescription(owner, true)+" around your asshole.</p>";
						break;
				}
				
			} else {
				switch(assHair) {
					case ZERO_NONE:
						transformation = UtilText.parse(owner, "<p>There is no longer any trace of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.namePos] asshole.</p>");
						break;
					case ONE_STUBBLE:
						transformation = UtilText.parse(owner, "<p>[npc.Name] now has a stubbly patch of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.her] asshole.</p>");
						break;
					case TWO_MANICURED:
						transformation = UtilText.parse(owner, "<p>[npc.Name] now has a well-manicured patch of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.her] asshole.</p>");
						break;
					case THREE_TRIMMED:
						transformation = UtilText.parse(owner, "<p>[npc.Name] now has a trimmed patch of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.her] asshole.</p>");
						break;
					case FOUR_NATURAL:
						transformation = UtilText.parse(owner, "<p>[npc.Name] now has a natural amount of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.her] asshole.</p>");
						break;
					case FIVE_UNKEMPT:
						transformation = UtilText.parse(owner, "<p>[npc.Name] now has a unkempt bush of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.her] asshole.</p>");
						break;
					case SIX_BUSHY:
						transformation = UtilText.parse(owner, "<p>[npc.Name] now has a thick, bushy mass of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.her] asshole.</p>");
						break;
					case SEVEN_WILD:
						transformation = UtilText.parse(owner, "<p>[npc.Name] now has a wild, bushy mass of "+getAssHairType(owner).getFullDescription(owner, true)+" around [npc.her] asshole.</p>");
						break;
				}
			}
		}


		this.assHair = assHair;

		return transformation;
	}

	@Override
	public String getWetnessChangeDescription(GameCharacter owner, int wetnessChange) {

		if (wetnessChange == 0) {
			if(owner.isPlayer())
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(Your [pc.asshole]'s wetness doesn't change...)]</p>");

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The wetness of [npc.namePos] [npc.asshole] doesn't change...)]</p>");
		}
		if(wetness < Wetness.SEVEN_DROOLING.getValue() && owner.getBodyMaterial().isOrificesAlwaysMaximumWetness()) {
			wetness = Wetness.SEVEN_DROOLING.getValue();
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourSex(Due to being made out of "+owner.getBodyMaterial().getName()+", your [pc.asshole] can't be anything but "+Wetness.SEVEN_DROOLING.getDescriptor()+"...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourSex(Due to being made out of "+owner.getBodyMaterial().getName()+", [npc.namePos] [npc.asshole] can't be anything but "+Wetness.SEVEN_DROOLING.getDescriptor()+"...)]</p>");
		}

		String wetnessDescriptor = getWetness(owner).getDescriptor();
		if (wetnessChange > 0) {
			if (owner.isPlayer())
				return UtilText.parse(owner, "<p>"
						+ "Your [pc.eyes] widen as you feel moisture beading around your asshole, and you let out [pc.a_moan+] as you realise that your rear entrance is lubricating itself and [style.boldGrow(getting wetter)].<br/>"
						+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " asshole)]!"
						+ "</p>");

			return UtilText.parse(owner, "<p>"
					+ "[npc.NamePos] [npc.eyes] widen as [npc.she] feels moisture beading around [npc.her] asshole,"
					+ " and [npc.she] lets out [npc.a_moan+] as [npc.she] realises that [npc.her] rear entrance is lubricating itself and [style.boldGrow(getting wetter)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " asshole)]!"
					+ "</p>");
		}

		if (owner.isPlayer())
			return UtilText.parse(owner, "<p>"
					+ "You shift about uncomfortably and let out a frustrated groan as you feel your rear entrance [style.boldShrink(getting drier)].<br/>"
					+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " asshole)]!"
					+ "</p>");

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] shifts about uncomfortably and lets out a frustrated groan as [npc.she] feels [npc.her] rear entrance [style.boldShrink(getting drier)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " asshole)]!"
				+ "</p>");
	}

	@Override
	public String getCapacityChangeDescription(GameCharacter owner, float capacityChange) {
		if (capacityChange != 0) {
			if(owner.isPlayer())
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(Your [pc.asshole]'s capacity doesn't change...)]</p>");

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The capacity of [npc.namePos] [npc.asshole] doesn't change...)]</p>");
		}
		String capacityDescriptor = getCapacity().getDescriptor();
		if (capacityChange > 0) {
			if (owner.isPlayer())
				return UtilText.parse(owner, "<p>"
						+ "You let out a shocked gasp as you feel your asshole dilate and stretch out as its internal [style.boldGrow(capacity increases)].<br/>"
						+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " asshole)]!"
						+ "</p>");

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a shocked gasp as [npc.she] feels [npc.her] asshole dilate and stretch out as its internal [style.boldGrow(capacity increases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " asshole)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return UtilText.parse(owner, "<p>"
					+ "You let out a cry as you feel your asshole uncontrollably tighten and clench as its internal [style.boldShrink(capacity decreases)].<br/>"
					+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " asshole)]!"
					+ "</p>");

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a cry as [npc.she] feels [npc.her] asshole uncontrollably tighten and clench as its internal [style.boldShrink(capacity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " asshole)]!"
				+ "</p>");
	}

	@Override
	public String getElasticityChangeDescription(GameCharacter owner, int elasticityChange) {
		if (elasticityChange == 0) {
			if(owner.isPlayer())
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(Your [pc.asshole]'s elasticity doesn't change...)]</p>");

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The elasticity of [npc.namePos] [npc.asshole] doesn't change...)]</p>");
		}
		String elasticityDescriptor = getElasticity().getDescriptor();
		if (elasticityChange > 0) {
			if (owner.isPlayer())
				return UtilText.parse(owner, "<p>"
						+ "You let out a little gasp as you feel a strange slackening sensation pulsating deep within your ass as your asshole's [style.boldGrow(elasticity increases)].<br/>"
						+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " asshole)]!"
						+ "</p>");

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange slackening sensation pulsating deep within [npc.her] ass as [npc.her] asshole's [style.boldGrow(elasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " asshole)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return UtilText.parse(owner, "<p>"
					+ "You let out a little gasp as you feel a strange clenching sensation pulsating deep within your ass as your asshole's [style.boldShrink(elasticity decreases)].<br/>"
					+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " asshole)]!"
					+ "</p>");

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange clenching sensation pulsating deep within [npc.her] ass as [npc.her] asshole's [style.boldShrink(elasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " asshole)]!"
				+ "</p>");

	}

	@Override
	public String getPlasticityChangeDescription(GameCharacter owner, int plasticityChange) {
		if (plasticityChange == 0) {
			if(owner.isPlayer())
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(Your [pc.asshole]'s plasticity doesn't change...)]</p>");

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The plasticity of [npc.namePos] [npc.asshole] doesn't change...)]</p>");
		}
		String plasticityDescriptor = getPlasticity().getDescriptor();
		if (plasticityChange > 0) {
			if (owner.isPlayer())
				return UtilText.parse(owner, "<p>"
						+ "You let out a little gasp as you feel a strange moulding sensation pulsating deep within your ass as your asshole's [style.boldGrow(plasticity increases)].<br/>"
						+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " asshole)]!"
						+ "</p>");

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange moulding sensation pulsating deep within [npc.her] ass as [npc.her] asshole's [style.boldGrow(plasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " asshole)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return UtilText.parse(owner, "<p>"
					+ "You let out a little gasp as you feel a strange softening sensation pulsating deep within your ass as your asshole's [style.boldShrink(plasticity decreases)].<br/>"
					+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " asshole)]!"
					+ "</p>");
		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange softening sensation pulsating deep within [npc.her] ass as [npc.her] asshole's [style.boldShrink(plasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " asshole)]!"
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
								+ "You feel an intense pressure swelling up deep within your [pc.ass], but before you have any chance to react, the feeling suddenly fades away."
								+ " With an experimental clench, you discover that the interior of your [pc.asshole] is now lined with [style.boldGrow(extra muscles)], which you can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex(Your asshole is now lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.ass], but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] [npc.asshole] is now lined with [style.boldGrow(extra muscles)],"
									+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] asshole is now lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your [pc.ass], but before you have any chance to react, the feeling suddenly fades away."
								+ " Shifting your [pc.ass] around a little, you feel that the inside of your [pc.asshole] is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)], which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex(Your asshole is now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.ass], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] [npc.ass] around a little, [npc.she] discovers that the inside of [npc.her] [npc.asshole] is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
							+ "[style.boldSex([npc.NamePos] asshole is now lined with fleshy, pleasure-inducing ribs!)]"
						+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your [pc.ass], but before you have any chance to react, the feeling suddenly fades away."
								+ " A surprised cry bursts out from your mouth as you feel that the inside of your [pc.asshole] is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which you have limited control.<br/>"
								+ "[style.boldSex(The inside of your asshole is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.ass], but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the inside of [npc.her] [npc.asshole] is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which [npc.she] has limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] asshole is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your [pc.ass], and you let out a little cry as you feel the rim of your [pc.asshole] [style.boldGrow(puff up)] into a doughnut-like ring.<br/>"
								+ "[style.boldSex(The rim of your asshole is now swollen and puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.ass], before the rim of [npc.her] [npc.asshole] [style.boldGrow(puffs up)] into a doughnut-like ring.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] asshole is now swollen and puffy!)]"
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
								+ "You feel an intense pressure swelling up deep within your [pc.ass], but before you have any chance to react, the feeling suddenly fades away."
								+ " With an experimental clench, you discover that the interior of your [pc.asshole] has lost its [style.boldShrink(extra muscles)].<br/>"
								+ "[style.boldSex(Your asshole is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.ass], but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] [npc.asshole] has lost its [style.boldShrink(extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] asshole is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your [pc.ass], but before you have any chance to react, the feeling suddenly fades away."
								+ " Shifting your [pc.ass] around a little, you feel that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined your [pc.asshole] have vanished.<br/>"
								+ "[style.boldSex(Your asshole is no longer lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.ass], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] [npc.ass] around a little, [npc.she] discovers that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined [npc.her] [npc.asshole] have vanished.<br/>"
							+ "[style.boldSex([npc.NamePos] asshole is no longer lined with fleshy, pleasure-inducing ribs!)]"
						+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your [pc.ass], but before you have any chance to react, the feeling suddenly fades away."
								+ " A surprised cry bursts out from your mouth as you feel that the [style.boldShrink(series of little wriggling tentacles)] within your [pc.asshole] have all disappeared.<br/>"
								+ "[style.boldSex(The inside of your asshole is no longer filled with little tentacles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.ass], but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the [style.boldShrink(series of little wriggling tentacles)] within [npc.her] [npc.asshole] have all disappeared.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] asshole is no longer filled with little tentacles!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your [pc.ass], and you let out a little cry as you feel the puffy rim of your [pc.asshole] [style.boldGrow(deflate)] into a more normal-looking shape.<br/>"
								+ "[style.boldSex(The rim of your asshole is no longer swollen and puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.ass],"
									+ " before the puffy rim of [npc.her] [npc.asshole] [style.boldShrink(deflates)] into a more normal-looking shape.<br/>"
								+ "[style.boldSex(The rim of [npc.namePos] asshole is no longer swollen and puffy!)]"
							+ "</p>";
				}
		}

		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}
}
