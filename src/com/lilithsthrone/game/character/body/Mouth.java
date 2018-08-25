package com.lilithsthrone.game.character.body;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.FluidType;
import com.lilithsthrone.game.character.body.types.MouthType;
import com.lilithsthrone.game.character.body.valueEnums.LipSize;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.InventorySlot;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Colour;

/**
 * @since 0.1.83
 * @version 0.1.83
 * @author Innoxia
 */
public class Mouth extends AbstractOrifice implements BodyPartInterface, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected MouthType type;
	protected int lipSize;
	protected boolean piercedLip;
	Map<FluidType, Integer> contents;

	public Mouth(MouthType type, int lipSize, int wetness, int capacity, int elasticity, int plasticity, boolean virgin) {
		super(wetness, capacity, elasticity, plasticity, virgin, type.getDefaultRacialOrificeModifiers());
		this.type = type;
		this.lipSize = lipSize;
	}

	@Override
	public MouthType getType() {
		return type;
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
		return type.getNameSingular(owner);
	}

	@Override
	public String getNamePlural(GameCharacter owner) {
		return type.getNamePlural(owner);
	}

	@Override
	public String getDescriptor(GameCharacter owner) {
		List<String> descriptorList = new ArrayList<>();
		
		for(OrificeModifier om : getOrificeModifiers()) {
			descriptorList.add(om.getName());
		}
		descriptorList.add(type.getDescriptor(owner));
		
		return UtilText.returnStringAtRandom(descriptorList.toArray(new String[]{}));
	}
	
	public String getLipsNameSingular(GameCharacter gc) {
		return UtilText.returnStringAtRandom("lip");
	}
	
	public String getLipsNamePlural(GameCharacter gc) {
		return UtilText.returnStringAtRandom("lips");
	}

	public String getLipsDescriptor(GameCharacter gc) {
		List<String> descriptorList = new ArrayList<>();
		
		if(!Main.game.isInSex() || getLipSize()!=LipSize.ONE_AVERAGE) {
			descriptorList.add(getLipSize().getName());
		}
		
		if (gc.isFeminine()) {
			descriptorList.add("soft");
			descriptorList.add("delicate");
		}
		
		return UtilText.returnStringAtRandom(descriptorList.toArray(new String[]{}));
	}

	public void setType(MouthType type) {
		this.type = type;
	}

	public LipSize getLipSize() {
		return LipSize.getLipSizeFromInt(lipSize);
	}
	
	public int getLipSizeValue() {
		return lipSize;
	}

	public String setLipSize(GameCharacter owner, int lipSize) {
		int effectiveLipSize = Math.max(0, Math.min(lipSize, LipSize.getLargest()));
		if(owner.getLipSizeValue() == effectiveLipSize) {
			if(owner.isPlayer()) {
				return "<p style='text-align:center;'>[style.colourDisabled(The size of your [pc.lips] doesn't change...)]</p>";
			} else {
				return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The size of [npc.namePos] [npc.lips] doesn't change...)]</p>");
			}
		}
		
		String transformation = "";
		
		if(this.lipSize > effectiveLipSize) {
			if(owner.isPlayer()) {
				transformation = "<p>A soothing coolness rises up into your [pc.lips], causing you to let out a surprised gasp as you feel them [style.boldShrink(shrinking)].<br/>";
			} else {
				transformation = UtilText.parse(owner, "<p>[npc.Name] lets out a little cry as [npc.she] feels a soothing coolness rise up into [npc.her] [npc.lips], before they suddenly [style.boldShrink(shrink)].<br/>");
			}
			
		} else {
			if(owner.isPlayer()) {
				transformation = "<p>A pulsating warmth rises up into your [pc.lips], causing you to let out a surprised gasp as you feel them [style.boldGrow(growing larger)].<br/>";
			} else {
				transformation = UtilText.parse(owner, "<p>[npc.Name] lets out a little cry as [npc.she] feels a pulsating warmth rise up into [npc.her] [npc.lips], before they suddenly [style.boldGrow(grow larger)].<br/>");
			}
		}
		
		this.lipSize = effectiveLipSize;

		if(owner.isPlayer()) {
			return transformation
				+ "You now have [style.boldSex([pc.lipSize] [pc.lips])]!</p>";
		} else {
			return transformation
					+ UtilText.parse(owner, "[npc.Name] now has [style.boldSex([npc.lipSize] [npc.lips])]!</p>");
		}
	}
	
	public boolean isPiercedLip() {
		return piercedLip;
	}
	
	public String setPiercedLip(GameCharacter owner, boolean piercedLip) {
		if(owner.isPiercedLip() == piercedLip) {
			return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
		}
		
		this.piercedLip = piercedLip;
		
		if(piercedLip) {
			if(owner.isPlayer()) {
				return "<p>Your [pc.lips] are now [style.boldGrow(pierced)]!</p>";
			} else {
				return UtilText.parse(owner,
						"<p>[npc.NamePos] [npc.lips] are now [style.boldGrow(pierced)]!</p>");
			}
			
		} else {
			AbstractClothing c = owner.getClothingInSlot(InventorySlot.PIERCING_LIP);
			String piercingUnequip = "";
			if(c!=null) {
				owner.forceUnequipClothingIntoVoid(owner, c);
				piercingUnequip = owner.addClothing(c, false);
			}
			
			if(owner.isPlayer()) {
				return "<p>"
							+ "Your [pc.lips] are [style.boldShrink(no longer pierced)]!"
						+ "</p>"
						+piercingUnequip;
			} else {
				return UtilText.parse(owner,
						"<p>"
								+ "[npc.NamePos] [npc.lips] are [style.boldShrink(no longer pierced)]!"
						+ "</p>"
						+piercingUnequip);
			}
		}
	}

	@Override
	public String getWetnessChangeDescription(GameCharacter owner, int wetnessChange) {
		if (wetnessChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your throat's wetness doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The wetness of [npc.namePos] throat doesn't change...)]</p>");
		}

		String wetnessDescriptor = getWetness(owner).getDescriptor();
		if (wetnessChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "Your throat suddenly fills with saliva, and you gulp as you realise that it's permanently [style.boldGrow(got wetter)].<br/>"
				+ "You now have [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " throat)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.NamePos] throat suddenly fills with saliva, and [npc.she] gulps as [npc.she] realises that it's permanently [style.boldGrow(got wetter)].<br/>"
					+ "[npc.Name] now has [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " throat)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You shift about uncomfortably as you feel your throat [style.boldShrink(getting drier)].<br/>"
			+ "You now have [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " throat)]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] shifts about uncomfortably as [npc.she] feels [npc.her] throat [style.boldShrink(getting drier)].<br/>"
				+ "[npc.Name] now has [style.boldSex(" + UtilText.generateSingularDeterminer(wetnessDescriptor) + " " + wetnessDescriptor + " throat)]!"
				+ "</p>");
	}

	@Override
	public String getCapacityChangeDescription(GameCharacter owner, float capacityChange) {
		if (capacityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your throat's capacity doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The capacity of [npc.namePos] throat doesn't change...)]</p>");
		}
		String capacityDescriptor = getCapacity().getDescriptor();
		if (capacityChange > 0)  {

			if (owner.isPlayer())
				return "<p>"
				+ "You feel your throat's [style.boldGrow(capacity increasing)] as it relaxes and stretches out with a mind of its own.<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " throat)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] feels [npc.her] throat's [style.boldGrow(capacity increasing)] as it relaxes and stretches out with a mind of its own.<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " throat)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You let out a cry as you feel your throat close up and tighten as its internal [style.boldShrink(capacity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " throat)]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a cry as [npc.she] feels [npc.her] throat close up and tighten as its internal [style.boldShrink(capacity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(capacityDescriptor) + " " + capacityDescriptor + " throat)]!"
				+ "</p>");
	}

	@Override
	public String getElasticityChangeDescription(GameCharacter owner, int elasticityChange) {
		if (elasticityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your throat's elasticity doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The elasticity of [npc.namePos] throat doesn't change...)]</p>");
		}
		String elasticityDescriptor = getElasticity().getDescriptor();
		if (elasticityChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange slackening sensation pulsating deep within your throat as its [style.boldGrow(elasticity increases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " throat)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange slackening sensation pulsating deep within [npc.her] throat as its [style.boldGrow(elasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " throat)]!"
					+ "</p>");
		}

		if (owner.isPlayer())
			return "<p>"
			+ "You let out a little gasp as you feel a strange clenching sensation pulsating deep within your throat as its [style.boldShrink(elasticity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " throat)]!"
			+ "</p>";

		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange clenching sensation pulsating deep within [npc.her] throat as its [style.boldShrink(elasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(elasticityDescriptor) + " " + elasticityDescriptor + " throat)]!"
				+ "</p>");
	}

	@Override
	public String getPlasticityChangeDescription(GameCharacter owner, int plasticityChange) {
		if (plasticityChange == 0) {
			if(owner.isPlayer())
				return "<p style='text-align:center;'>[style.colourDisabled(Your throat's plasticity doesn't change...)]</p>";

			return UtilText.parse(owner, "<p style='text-align:center;'>[style.colourDisabled(The plasticity of [npc.namePos] throat doesn't change...)]</p>");
		}
		String plasticityDescriptor = getPlasticity().getDescriptor();
		if (plasticityChange > 0) {
			if (owner.isPlayer())
				return "<p>"
				+ "You let out a little gasp as you feel a strange moulding sensation pulsating deep within your throat as its [style.boldGrow(plasticity increases)].<br/>"
				+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " throat)]!"
				+ "</p>";

			return UtilText.parse(owner, "<p>"
					+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange moulding sensation pulsating deep within [npc.her] throat as its [style.boldGrow(plasticity increases)].<br/>"
					+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " throat)]!"
					+ "</p>");
		}
		if (owner.isPlayer())
			return "<p>"
			+ "You let out a little gasp as you feel a strange softening sensation pulsating deep within your throat as its [style.boldShrink(plasticity decreases)].<br/>"
			+ "The transformation quickly passes, leaving you with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " throat)]!"
			+ "</p>";
		return UtilText.parse(owner, "<p>"
				+ "[npc.Name] lets out a little gasp as [npc.she] feels a strange softening sensation pulsating deep within [npc.her] throat as its [style.boldShrink(plasticity decreases)].<br/>"
				+ "The transformation quickly passes, leaving [npc.herHim] with [style.boldSex(" + UtilText.generateSingularDeterminer(plasticityDescriptor) + " " + plasticityDescriptor + " throat)]!"
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
								+ "You feel an intense pressure swelling up deep within your throat, but before you have any chance to react, the feeling suddenly fades away."
								+ " With an experimental clench, you discover that the interior of your throat is now lined with [style.boldGrow(extra muscles)], which you can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex(Your throat is now lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] throat is now lined with [style.boldGrow(extra muscles)],"
									+ " which [npc.she] can use to expertly grip and squeeze down on any penetrating object.<br/>"
								+ "[style.boldSex([npc.NamePos] throat is now lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your throat, but before you have any chance to react, the feeling suddenly fades away."
								+ " Shifting your throat around a little, you feel that the inside of your throat is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)], which provide extreme pleasure when stimulated.<br/>"
								+ "[style.boldSex(Your throat is now lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] throat around a little, [npc.she] discovers that the inside of [npc.her] throat is now lined with [style.boldGrow(fleshy, highly-sensitive ribs)],"
								+ " which provide extreme pleasure when stimulated.<br/>"
							+ "[style.boldSex([npc.NamePos] throat is now lined with fleshy, pleasure-inducing ribs!)]"
						+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your throat, but before you have any chance to react, the feeling suddenly fades away."
								+ " A surprised cry bursts out from your mouth as you feel that the inside of your throat is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which you have limited control.<br/>"
								+ "[style.boldSex(The inside of your throat is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the inside of [npc.her] throat is now filled with [style.boldGrow(a series of little wriggling tentacles)], over which [npc.she] has limited control.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] throat is now filled with little tentacles, which wriggle with a mind of their own!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your mouth, and you let out a little cry as you feel your lips swell out and [style.boldGrow(puff up)].<br/>"
								+ "[style.boldSex(Your lips are now extremely puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over your mouth, before [npc.her] lips swell out and [style.boldGrow(puff up)].<br/>"
								+ "[style.boldSex([npc.NamePos] lips are now extremely puffy!)]"
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
								+ "You feel an intense pressure swelling up deep within your throat, but before you have any chance to react, the feeling suddenly fades away."
								+ " With an experimental clench, you discover that the interior of your throat has lost its [style.boldShrink(extra muscles)].<br/>"
								+ "[style.boldSex(Your throat is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the interior of [npc.her] throat has lost its [style.boldShrink(extra muscles)].<br/>"
								+ "[style.boldSex([npc.NamePos] throat is no longer lined with an intricate series of muscles!)]"
							+ "</p>";
				}
			case RIBBED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your throat, but before you have any chance to react, the feeling suddenly fades away."
								+ " Shifting your throat around a little, you feel that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined your throat have vanished.<br/>"
								+ "[style.boldSex(Your throat is no longer lined with fleshy, pleasure-inducing ribs!)]"
							+ "</p>";
				} else {
					return "<p>"
							+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.she] has any chance to react, the feeling quickly dissipates."
							+ " Shifting [npc.her] throat around a little, [npc.she] discovers that the [style.boldShrink(fleshy, highly-sensitive ribs)] that once lined [npc.her] throat have vanished.<br/>"
							+ "[style.boldSex([npc.NamePos] throat is no longer lined with fleshy, pleasure-inducing ribs!)]"
						+ "</p>";
				}
			case TENTACLED:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel an intense pressure swelling up deep within your throat, but before you have any chance to react, the feeling suddenly fades away."
								+ " A surprised cry bursts out from your mouth as you feel that the [style.boldShrink(series of little wriggling tentacles)] within your throat have all disappeared.<br/>"
								+ "[style.boldSex(The inside of your throat is no longer filled with little tentacles!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as an intense pressure swells up deep within [npc.her] throat, but before [npc.she] has any chance to react, the feeling quickly dissipates."
								+ " With an experimental clench, [npc.she] discovers that the [style.boldShrink(series of little wriggling tentacles)] within [npc.her] throat have all disappeared.<br/>"
								+ "[style.boldSex(The inside of [npc.namePos] throat is no longer filled with little tentacles!)]"
							+ "</p>";
				}
			case PUFFY:
				if(owner.isPlayer()) {
					return "<p>"
								+ "You feel a tingling sensation running over your mouth, and you let out a little cry as you feel your puffy lips [style.boldGrow(deflate)] into a more normal-looking size.<br/>"
								+ "[style.boldSex(Your lips are no longer extremely puffy!)]"
							+ "</p>";
				} else {
					return "<p>"
								+ "[npc.Name] lets out a little cry as [npc.she] feels a tingling sensation running over [npc.her] lips, before they suddenly [style.boldShrink(deflate)] into a more normal-looking size.<br/>"
								+ "[style.boldSex([npc.NamePos] lips are no longer extremely puffy!)]"
							+ "</p>";
				}
		}
		// Catch:
		return "<p style='text-align:center;'>[style.colourDisabled(Nothing happens...)]</p>";
	}
	public String savor(GameCharacter owner, FluidType fluid) {
		return "<p>" + addFluid(owner, fluid, false) + "</p>";
	}
	
	public String drink(GameCharacter owner, FluidType fluid) {
		return "<p>" + addFluid(owner, fluid, true) + "</p>";
	}

	private String getContentsDescription()
	{
		switch (this.contents.size()) {
		case 0:
			return "";
		case 1:
			Entry<FluidType, Integer> f = this.contents.entrySet().iterator().next();
			FluidType fluid = f.getKey();
			return "<b style='color:"+fluid.getRace().getColour().toWebHexString()+";'>" + (f.getValue() > 1 ? "a lot of " : "the ") + fluid.name() + "</b>";
		}
		return "<b style='color:" + Colour.CUM + ";'>a mixture of fluids</b>";
	}

	private String addFluid(GameCharacter owner, FluidType fluid, boolean autoSwallow) {

		String gulp = getContentsDescription();
		String ret = "You now add some ";

		if (gulp.isEmpty() || this.contents.containsKey(fluid) == false) {
			this.contents.put(fluid, 1);
		} else {
			this.contents.put(fluid, this.contents.get(fluid) + 1);
			ret += "more ";
		}

		ret += "<b style='color:" + fluid.getRace().getColour().toWebHexString() + ";'>"
				+ fluid.name() + "</b> in your mouth" + (this.contents.size() > 1 ? " as well" : "") + ".";

		int sum = 0;
		for (Entry<FluidType, Integer> e : this.contents.entrySet())
			sum += e.getValue();

		if (sum > this.capacity + this.elasticity) // the mouth is full
			ret += " But it is all too much to contain, as there is already " + gulp+ " in your mouth. "
					+ (autoSwallow || Math.random()>=0.8 ? this.swallow(owner) : this.spit(owner, 1));
		return ret;
	}

	public String swallow(GameCharacter owner) {
		String gulp = this.getContentsDescription();
		if (gulp.isEmpty())
			return "<p>You have nothing in your mouth.</p>";

		for (Entry<FluidType, Integer> e : this.contents.entrySet()) {
			switch (e.getKey().getBaseType()) {
				case CUM:
				case GIRLCUM:
					owner.addStatusEffect(StatusEffect.CREAMPIE_MOUTH, 10 * e.getValue());
					break;
				case MILK:
			}
		}
		this.contents.clear();
		return "<p>You swallow " + gulp + " that you had in your mouth.</p>";
	}

	public String spit(GameCharacter owner, int amount) {
		String ret = "";

		for(Iterator<Map.Entry<FluidType, Integer>> it = this.contents.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<FluidType, Integer> entry = it.next();
			int v = entry.getValue();
			if(amount == -1 || v <= amount)
				it.remove();
			else
				entry.setValue(v - amount);

			owner.addStatusEffect(StatusEffect.BODY_CUM, 10 * v);
		}
		return ret;
	}
}