package com.lilithsthrone.game.character.body;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.BodyPartTypeInterface;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.dialogue.utils.UtilText;

public class Urethra extends AbstractOrifice implements BodyPartInterface, Serializable {

	private static final long serialVersionUID = 1L;

	protected BodyPartTypeInterface type;

	public Urethra(BodyPartTypeInterface type, int wetness, float capacity, int elasticity, int plasticity, boolean virgin, Collection<OrificeModifier> orificeModifiers) {
		super(wetness, capacity, elasticity, plasticity, virgin, orificeModifiers);
		this.type = type;
		// TODO Auto-generated constructor stub
	}

	@Override
	public BodyPartTypeInterface getType() {
		return type;
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
		List<String> descriptorList = new ArrayList<>();

		for(OrificeModifier om : getOrificeModifiers()) {
			descriptorList.add(om.getName());
		}

		descriptorList.add(type.getDescriptor(owner));

		descriptorList.add(getCapacity().getDescriptor());

		return UtilText.returnStringAtRandom(descriptorList.toArray(new String[]{}));
	}

	@Override
	public String isChangeable(GameCharacter owner) {
		if (type instanceof VaginaType) {
			if (!owner.hasVagina()) {
				if(owner.isPlayer())
					return "<p style='text-align:center;'>[style.colourDisabled(You lack a vagina, so nothing happens...)]</p>";

				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] lacks a vagina, so nothing happens...)]</p>");
			}
			return "";
		}
		if (!owner.hasPenis()) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(You lack a penis, so nothing happens...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.Name] lacks a penis, so nothing happens...)]</p>");
		}
		return "";
	}

	@Override
	public String getWetnessChangeDescription(GameCharacter owner, int wetnessChange) {
		if (type instanceof VaginaType) {
			if (wetnessChange == 0) {
				if(owner.isPlayer())
					return "<p style='text-align:center;'>[style.colourDisabled(Your urethral wetness doesn't change...)]</p>";

				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.NamePos] urethral wetness doesn't change...)]</p>");
			}

			String wetnessDescriptor = getWetness(owner).getDescriptor();
			if (wetnessChange > 0) {
				if (owner.isPlayer())
					return "<p>"
					+ "Your [pc.eyes] widen as you feel a shudder of excitement run through your [pc.pussy+], and you realise that your urethra has gotten [style.boldGrow(wetter)].<br/>"
					+ "You now have [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
					+ "</p>";

				return UtilText.parse(owner, "<p>"
						+ "[npc.NamePos] [npc.eyes] widen as [npc.she] feels shudder of excitement run through [npc.her] [npc.pussy+], and [npc.she] realises that [npc.her] urethra has gotten [style.boldGrow(wetter)].<br/>"
						+ "[npc.She] now has [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
						+ "</p>");
			}
			if (owner.isPlayer())
				return "<p>"
				+ "You shift about uncomfortably and let out a frustrated groan as you feel your urethra getting [style.boldShrink(drier)].<br/>"
				+ "You now have [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] shifts about uncomfortably and lets out a frustrated groan as [npc.she] feels [npc.her] urethra getting [style.boldShrink(drier)].<br/>"
					+ "[npc.She] now has [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
					+ "</p>");
		}

		if (wetnessChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your precum production doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled([npc.NamePos] precum production doesn't change...)]</p>");
		}

		String wetnessDescriptor = getWetness(owner).getDescriptor();
		if (wetnessChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "Your [pc.eyes] widen as you feel your [pc.cock+] suddenly grow hard, and you let out [pc.a_moan+] as you feel a slick stream of precum oozing out of the tip as its production [style.boldGrow(increases)].<br/>"
				+ "You now have [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
				+ "</p>";

			return UtilText.parse(owner,"<p>"
							+ "[npc.NamePos] [npc.eyes] widen as [npc.she] feels [npc.her] [npc.cock+] suddenly grow hard, and [npc.she] lets out [npc.a_moan+] as a slick stream of precum oozes out of the tip as its production [style.boldGrow(increases)].<br/>"
							+ "[npc.She] now has [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
							+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You shift about uncomfortably and let out a frustrated groan as you feel your precum production [style.boldShrink(decrease)].<br/>"
			+ "You now have [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
						+ "[npc.Name] shifts about uncomfortably and lets out a frustrated groan as [npc.she] feels [npc.her] precum production [style.boldShrink(decrease)].<br/>"
						+ "[npc.She] now has [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " urethra)]!"
						+ "</p>");
	}

	@Override
	public String getCapacityChangeDescription(GameCharacter owner, float capacityChange) {
		if (capacityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your urethra's capacity doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The capacity of [npc.namePos] urethra doesn't change...)]</p>");
		}
		String capacityDescriptor = getCapacity().getDescriptor();
		if (capacityChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a shocked gasp as you feel your urethra dilate and stretch out as its internal [style.boldGrow(capacity increases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " urethra)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
							+ "[npc.Name] lets out a shocked gasp as [npc.she] feels [npc.her] urethra dilate and stretch out as its internal [style.boldGrow(capacity increases)].<br/>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " urethra)]!"
							+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You let out a cry as you feel your urethra uncontrollably tighten and clench as its internal [style.boldShrink(capacity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " urethra)]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a cry as [npc.she] feels [npc.her] urethra uncontrollably tighten and clench as its internal [style.boldShrink(capacity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " urethra)]!"
				+ "</p>");
	}

	@Override
	public String getElasticityChangeDescription(GameCharacter owner, int elasticityChange) {
		String elasticityDescriptor = getElasticity().getDescriptor();
		if (type instanceof VaginaType) {

			if (elasticityChange > 0) {
				if (owner.isPlayer())
					return "<p>"
					+ "You let out a little gasp as you feel a strange slackening sensation pulsating deep within your [pc.pussy] as your urethra's [style.boldGrow(elasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
					+ "</p>";

				return UtilText.parse(owner, "<p>"
						+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange slackening sensation pulsating deep within [npc.her] [npc.pussy] as [npc.her] urethra's [style.boldGrow(elasticity increases)].<br/>"
						+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
						+ "</p>");
			}
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange clenching sensation pulsating deep within your [pc.pussy] as your urethra's [style.boldShrink(elasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
							+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange clenching sensation pulsating deep within [npc.her] [npc.pussy] as [npc.her] urethra's [style.boldShrink(elasticity decreases)].<br/>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
							+ "</p>");
		}
		if (elasticityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your urethra's elasticity doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The elasticity of [npc.namePos] urethra doesn't change...)]</p>");
		}

		if (elasticityChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange slackening sensation pulsating deep within your [pc.cock] as your urethra's [style.boldGrow(elasticity increases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange slackening sensation pulsating deep within [npc.her] [npc.cock] as [npc.her] urethra's [style.boldGrow(elasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You let out a little gasp as you feel a strange clenching sensation pulsating deep within your [pc.cock] as your urethra's [style.boldShrink(elasticity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange clenching sensation pulsating deep within [npc.her] [npc.cock] as [npc.her] urethra's [style.boldShrink(elasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " urethra)]!"
				+ "</p>");
	}

	@Override
	public String getPlasticityChangeDescription(GameCharacter owner, int plasticityChange) {
		if (type instanceof VaginaType) {

			if (plasticityChange == 0) {
				if(owner.isPlayer())
					return "<p style='text-align:center;'>[style.colourDisabled(Your urethra's plasticity doesn't change...)]</p>";

				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The plasticity of [npc.namePos] urethra doesn't change...)]</p>");
			}

			String plasticityDescriptor = getPlasticity().getDescriptor();
			if (plasticityChange > 0) {
				if (owner.isPlayer())
					return "<p>"
					+ "You let out a little gasp as you feel a strange moulding sensation pulsating deep within your [pc.pussy] as your urethra's [style.boldGrow(plasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
					+ "</p>";

				return UtilText.parse(owner, "<p>"
						+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange moulding sensation pulsating deep within [npc.her] [npc.pussy] as [npc.her] urethra's [style.boldGrow(plasticity increases)].<br/>"
						+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
						+ "</p>");
			}
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange softening sensation pulsating deep within your [pc.pussy] as your urethra's [style.boldShrink(plasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange softening sensation pulsating deep within [npc.her] [npc.pussy] as [npc.her] urethra's [style.boldShrink(plasticity decreases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
					+ "</p>");
		}
		if (plasticityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your urethra's plasticity doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The plasticity of [npc.namePos] urethra doesn't change...)]</p>");
		}

		String plasticityDescriptor = getPlasticity().getDescriptor();
		if (plasticityChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange moulding sensation pulsating deep within your [pc.cock] as your urethra's [style.boldGrow(plasticity increases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
				+ "</p>";

			return UtilText.parse(owner,
					"<p>"
							+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange moulding sensation pulsating deep within [npc.her] [npc.cock] as [npc.her] urethra's [style.boldGrow(plasticity increases)].<br/>"
							+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
							+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You let out a little gasp as you feel a strange softening sensation pulsating deep within your [pc.cock] as your urethra's [style.boldShrink(plasticity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
			+ "</p>";

		return UtilText.parse(owner,
				"<p>"
						+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange softening sensation pulsating deep within [npc.her] [npc.cock] as [npc.her] urethra's [style.boldShrink(plasticity decreases)].<br/>"
						+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " urethra)]!"
						+ "</p>");
	}

	@Override
	public String addOrificeModifier(GameCharacter owner, OrificeModifier modifier) {
		if(hasOrificeModifier(modifier)) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}

		orificeModifiers.add(modifier);
		if (type instanceof VaginaType) {

			switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.pussy], but before you have any chance to react, the feeling suddenly fades away."
							+ " With an experimental clench, you discover that the interior of your urethra is now lined with [style.boldGrow(muscles)], which you can use to expertly grip and squeeze down on any penetrating object.<br/>"
							+ "[style.boldSex(Your urethra is now lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] urethra is now lined with [style.boldGrow(muscles)], which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is now lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.pussy], but before you have any chance to react, the feeling suddenly fades away."
							+ " Shifting your [pc.pussy] around a little, you feel that the inside of your urethra is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)], which provide extreme pleasure when stimulated.<br/>"
							+ "[style.boldSex(Your urethra is now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] [npc.pussy] around a little, [npc.she] discovers that the inside of [npc.her] urethra is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
							+ " which provide extreme pleasure when stimulated.<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.pussy], but before you have any chance to react, the feeling suddenly fades away."
							+ " A surprised cry bursts out from your mouth as you feel that the inside of your urethra is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which you have limited control.<br/>"
							+ "[style.boldSex(The inside of your urethra is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the inside of [npc.her] urethra is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which [npc.she] has limited control.<br/>"
							+ "[style.boldSex(The inside of [npc.namePos] urethra is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel a tingling sensation running over your [pc.pussy], and you let out a little cry as you feel the rim of your urethra [style.boldGrow(puff up)] into a doughnut-like ring.<br/>"
							+ "[style.boldSex(The rim of your urethra is now swollen and puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.pussy], before the rim of [npc.her] urethra [style.boldGrow(puffs up)] into a doughnut-like ring.<br/>"
							+ "[style.boldSex(The rim of [npc.namePos] urethra is now swollen and puffy!)]"
							+ "</p>";
				}
			}
		} else {
			switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.cock], but before you have any chance to react, the feeling suddenly fades away."
							+ " With an experimental clench, you discover that the interior of your urethra is now lined with [style.boldGrow(muscles)], which you can use to expertly grip and squeeze down on any penetrating object.<br/>"
							+ "[style.boldSex(Your urethra is now lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.cock], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] urethra is now lined with [style.boldGrow(muscles)], which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is now lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.cock], but before you have any chance to react, the feeling suddenly fades away."
							+ " Shifting your [pc.cock] around a little, you feel that the inside of your urethra is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)], which provide extreme pleasure when stimulated.<br/>"
							+ "[style.boldSex(Your urethra is now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.cock], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] [npc.cock] around a little, [npc.she] discovers that the inside of [npc.her] urethra is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
							+ " which provide extreme pleasure when stimulated.<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.cock], but before you have any chance to react, the feeling suddenly fades away."
							+ " A surprised cry bursts out from your mouth as you feel that the inside of your urethra is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which you have limited control.<br/>"
							+ "[style.boldSex(The inside of your urethra is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.cock], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the inside of [npc.her] urethra is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which [npc.she] has limited control.<br/>"
							+ "[style.boldSex(The inside of [npc.namePos] urethra is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel a tingling sensation running over your [pc.cock], and you let out a little cry as you feel the rim of your urethra [style.boldGrow(puff up)] into a doughnut-like ring.<br/>"
							+ "[style.boldSex(The rim of your urethra is now swollen and puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.cock], before the rim of [npc.her] urethra [style.boldGrow(puffs up)] into a doughnut-like ring.<br/>"
							+ "[style.boldSex(The rim of [npc.namePos] urethra is now swollen and puffy!)]"
							+ "</p>";
				}
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

		if (type instanceof VaginaType) {
			switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.pussy], but before you have any chance to react, the feeling suddenly fades away."
							+ " With an experimental clench, you discover that the interior of your urethra has lost its [style.boldShrink(extra muscles)].<br/>"
							+ "[style.boldSex(Your urethra is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] urethra has lost its [style.boldShrink(extra muscles)].<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.pussy], but before you have any chance to react, the feeling suddenly fades away."
							+ " Shifting your [pc.pussy] around a little, you feel that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined your urethra have vanished.<br/>"
							+ "[style.boldSex(Your urethra is no longer lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] [npc.pussy] around a little, [npc.she] discovers that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined [npc.her] urethra have vanished.<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is no longer lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.pussy], but before you have any chance to react, the feeling suddenly fades away."
							+ " A surprised cry bursts out from your mouth as you feel that the [style.boldShrink(series of little wriggling tentacles)] within your urethra have all disappeared.<br/>"
							+ "[style.boldSex(The inside of your urethra is no longer filled with little tentacles!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.pussy], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the [style.boldShrink(series of little wriggling tentacles)] within [npc.her] urethra have all disappeared.<br/>"
							+ "[style.boldSex(The inside of [npc.namePos] urethra is no longer filled with little tentacles!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel a tingling sensation running over your [pc.pussy], and you let out a little cry as you feel the puffy rim of your urethra [style.boldGrow(deflate)] into a more normal-looking shape.<br/>"
							+ "[style.boldSex(The rim of your urethra is no longer swollen and puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.pussy],"
							+ " before the puffy rim of [npc.her] urethra [style.boldShrink(deflates)] into a more normal-looking shape.<br/>"
							+ "[style.boldSex(The rim of [npc.namePos] urethra is no longer swollen and puffy!)]"
							+ "</p>";
				}
			}
		} else {
			switch(modifier) {
			case MUSCLE_CONTROL:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.cock], but before you have any chance to react, the feeling suddenly fades away."
							+ " With an experimental clench, you discover that the interior of your urethra has lost its [style.boldShrink(extra muscles)].<br/>"
							+ "[style.boldSex(Your urethra is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.cock], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] urethra has lost its [style.boldShrink(extra muscles)].<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.cock], but before you have any chance to react, the feeling suddenly fades away."
							+ " Shifting your [pc.cock] around a little, you feel that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined your urethra have vanished.<br/>"
							+ "[style.boldSex(Your urethra is no longer lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.cock], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] [npc.cock] around a little, [npc.she] discovers that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined [npc.her] urethra have vanished.<br/>"
							+ "[style.boldSex([npc.NamePos] urethra is no longer lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel an intense pressure swelling up deep within your [pc.cock], but before you have any chance to react, the feeling suddenly fades away."
							+ " A surprised cry bursts out from your mouth as you feel that the [style.boldShrink(series of little wriggling tentacles)] within your urethra have all disappeared.<br/>"
							+ "[style.boldSex(The inside of your urethra is no longer filled with little tentacles!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] [npc.cock], but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " With an experimental clench, [npc.she] discovers that the [style.boldShrink(series of little wriggling tentacles)] within [npc.her] urethra have all disappeared.<br/>"
							+ "[style.boldSex(The inside of [npc.namePos] urethra is no longer filled with little tentacles!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
							+ "You feel a tingling sensation running over your [pc.cock], and you let out a little cry as you feel the puffy rim of your urethra [style.boldGrow(deflate)] into a more normal-looking shape.<br/>"
							+ "[style.boldSex(The rim of your urethra is no longer swollen and puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] [npc.cock],"
							+ " before the puffy rim of [npc.her] urethra [style.boldShrink(deflates)] into a more normal-looking shape.<br/>"
							+ "[style.boldSex(The rim of [npc.namePos] urethra is no longer swollen and puffy!)]"
							+ "</p>";
				}
			}
		}
		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}
}
