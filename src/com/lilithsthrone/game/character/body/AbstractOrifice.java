package com.lilithsthrone.game.character.body;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.OrificeElasticity;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.body.valueEnums.OrificePlasticity;
import com.lilithsthrone.game.character.body.valueEnums.Wetness;

public abstract class AbstractOrifice {

	protected int wetness;
	protected int elasticity;
	protected int plasticity;
	protected float capacity;
	protected float stretchedCapacity;
	protected boolean virgin;
	protected Set<OrificeModifier> orificeModifiers;

	public AbstractOrifice(int wetness, float capacity, int elasticity, int plasticity, boolean virgin, Collection<OrificeModifier> orificeModifiers) {
		this.wetness = wetness;
		this.capacity = capacity;
		stretchedCapacity = capacity;
		this.elasticity = elasticity;
		this.plasticity = plasticity;
		this.virgin = virgin;

		this.orificeModifiers = new HashSet<>(orificeModifiers);
	}
	public abstract String getName(GameCharacter owner);

	public Wetness getWetness(GameCharacter owner) {
		return Wetness.valueOf(wetness);
	}

	public String isChangeable(GameCharacter owner) {
		return "";
	}

	public abstract String getWetnessChangeDescription(GameCharacter owner, int wetnessChange);

	public String setWetness(GameCharacter owner, int newWetness) {
		String objection = isChangeable(owner);
		if (objection.equals("") == false)
			return objection;

		int change = Math.max(0, Math.min(newWetness, Wetness.SEVEN_DROOLING.getValue())) - wetness;
		wetness += change;
		return getWetnessChangeDescription(owner, change);
	}

	public Capacity getCapacity() {
		return Capacity.getCapacityFromValue(capacity);
	}

	public float getRawCapacityValue() {
		return capacity;
	}

	public abstract String getCapacityChangeDescription(GameCharacter owner, float capacityChange);

	public String setCapacity(GameCharacter owner, float newCapacity, boolean setStretchedValueToNewValue) {
		String objection = isChangeable(owner);
		if (objection.equals("") == false)
			return objection;

		float change = Math.max(0, Math.min(newCapacity, Capacity.SEVEN_GAPING.getMaximumValue())) - capacity;
		capacity += change;
		if(setStretchedValueToNewValue)
			stretchedCapacity = capacity;

		return getCapacityChangeDescription(owner, change);
	}

	public float getStretchedCapacity() {
		return stretchedCapacity;
	}

	public boolean setStretchedCapacity(float stretchedCapacity) {
		float oldStretchedCapacity = this.stretchedCapacity;
		this.stretchedCapacity = Math.max(0, Math.min(stretchedCapacity, Capacity.SEVEN_GAPING.getMaximumValue()));
		return oldStretchedCapacity != this.stretchedCapacity;
	}

	public OrificeElasticity getElasticity() {
		return OrificeElasticity.getElasticityFromInt(elasticity);
	}

	public abstract String getElasticityChangeDescription(GameCharacter owner, int elasticityChange);

	public String setElasticity(GameCharacter owner, int newElasticity) {
		String objection = isChangeable(owner);
		if (objection.equals("") == false)
			return objection;

		int change = Math.max(0, Math.min(newElasticity, OrificeElasticity.SEVEN_ELASTIC.getValue())) - elasticity;
		elasticity += change;
		return getElasticityChangeDescription(owner, change);
	}

	public OrificePlasticity getPlasticity() {
		return OrificePlasticity.getElasticityFromInt(plasticity);
	}

	public abstract String getPlasticityChangeDescription(GameCharacter owner, int plasticityChange);

	public String setPlasticity(GameCharacter owner, int newPlasticity) {
		String objection = isChangeable(owner);
		if (objection.equals("") == false)
			return objection;

		int change = Math.max(0, Math.min(newPlasticity, OrificePlasticity.SEVEN_MOULDABLE.getValue())) - plasticity;
		plasticity += change;
		return getPlasticityChangeDescription(owner, change);
	}

	public boolean isVirgin() {
		return virgin;
	}

	public void setVirgin(boolean virgin) {
		this.virgin = virgin;
	}

	public boolean hasOrificeModifier(OrificeModifier modifier) {
		return orificeModifiers.contains(modifier);
	}

	public abstract String addOrificeModifier(GameCharacter owner, OrificeModifier modifier);
	public abstract String removeOrificeModifier(GameCharacter owner, OrificeModifier modifier);

	public Set<OrificeModifier> getOrificeModifiers() {
		return orificeModifiers;
	}
}
