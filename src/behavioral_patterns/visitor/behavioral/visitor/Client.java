package behavioral.visitor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

abstract class Equipment {
	private String name;
	
	public Equipment(String name) {
		this.name = name;
	}
	
	public abstract Watt getPower();
	public abstract BigDecimal getNetPrice();
	public abstract BigDecimal getDiscountPrice();

	public abstract void accept(EquipmentVisitor visitor);
}

class FloppyDisk extends Equipment {

	public FloppyDisk(String name) {
		super(name);
	}

	@Override
	public Watt getPower() {
		return new Watt(1);
	}

	@Override
	public BigDecimal getNetPrice() {
		return new BigDecimal(1);
	}

	@Override
	public BigDecimal getDiscountPrice() {
		return new BigDecimal(5);
	}

	@Override
	public void accept(EquipmentVisitor visitor) {
		visitor.visit(this);
	}
	
}

class Chassis extends Equipment {
	private List<Equipment> equipments = new ArrayList<Equipment>();

	public Chassis(String name) {
		super(name);
	}

	@Override
	public Watt getPower() {
		return new Watt(3);
	}

	@Override
	public BigDecimal getNetPrice() {
		return new BigDecimal(2);
	}

	@Override
	public BigDecimal getDiscountPrice() {
		return new BigDecimal(10);
	}

	@Override
	public void accept(EquipmentVisitor visitor) {
		for (Equipment equipment : equipments) {
			equipment.accept(visitor);
		}
		visitor.visit(this);
	}

	public void add(Equipment equipment) {
		this.equipments.add(equipment);
	}
}

class Card extends Equipment {

	public Card(String name) {
		super(name);
	}

	@Override
	public Watt getPower() {
		return new Watt(76);
	}

	@Override
	public BigDecimal getNetPrice() {
		return new BigDecimal(15);
	}

	@Override
	public BigDecimal getDiscountPrice() {
		return new BigDecimal(11);
	}

	@Override
	public void accept(EquipmentVisitor visitor) {
		visitor.visit(this);
	}

}

class Bus extends Equipment {

	public Bus(String name) {
		super(name);
	}

	@Override
	public Watt getPower() {
		return new Watt(3);
	}

	@Override
	public BigDecimal getNetPrice() {
		return new BigDecimal(52);
	}

	@Override
	public BigDecimal getDiscountPrice() {
		return new BigDecimal(21);
	}

	@Override
	public void accept(EquipmentVisitor visitor) {
		visitor.visit(this);
	}

}

class Watt {

	private int value;

	public Watt(int i) {
		this.value = i;
	}
	
	public int getValue() {
		return value;
	}
}

abstract class EquipmentVisitor {

	public void visit(Bus bus) {
	}

	public void visit(Card card) {
	}

	public void visit(Chassis chassis) {
	}

	public void visit(FloppyDisk floppyDisk) {
	}
}

// Using this kind of visitor, instead of one which detects what type of object
// the visitor can visit, like the one I implemented, requires us to duplicate
// code across a set of visit operations. It can be diminished by creating a
// unique point of code to which the other methods would redirect.

// On the other hand, it could be faster, since there is no need to use
// reflection.
class PricingVisitor extends EquipmentVisitor {
	private BigDecimal totalPrice = new BigDecimal(0);
	
	@Override
	public void visit(Bus bus) {
		this.totalPrice = totalPrice.add(bus.getNetPrice());
	}

	@Override
	public void visit(Card card) {
		this.totalPrice = totalPrice.add(card.getNetPrice());
	}

	@Override
	public void visit(Chassis chassis) {
		this.totalPrice = totalPrice.add(chassis.getNetPrice());
	}

	@Override
	public void visit(FloppyDisk floppyDisk) {
		this.totalPrice = totalPrice.add(floppyDisk.getNetPrice());
	}

	public BigDecimal getTotal() {
		return totalPrice;
	}
}

public class Client {

	public static void main(String[] args) {
		Chassis chassis = new Chassis("main");
		chassis.add(new FloppyDisk("A"));
		chassis.add(new Bus("64bitBus"));
		chassis.add(new Card("SDCard"));
		
		PricingVisitor visitor = new PricingVisitor();
		chassis.accept(visitor);
		System.out.println("Total value of equipments: " + visitor.getTotal());
	}
}
