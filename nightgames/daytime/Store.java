package nightgames.daytime;

import nightgames.characters.Character;
import nightgames.global.Global;
import nightgames.items.Item;
import nightgames.items.clothing.Clothing;

import java.util.HashMap;

public abstract class Store extends Activity {
	protected HashMap<Item,Integer> stock;
	protected HashMap<Clothing,Integer> clothingstock;
	protected boolean acted;
	public Store(String name,Character player) {
		super(name,player);
		stock=new HashMap<Item,Integer>();
		clothingstock=new HashMap<Clothing,Integer>();
		acted=false;
	}

	@Override
	public abstract boolean known();

	@Override
	public abstract void visit(String choice);

	public void add(Item item){
		stock.put(item, item.getPrice());
	}
	public void add(Clothing item){
		clothingstock.put(item, item.getPrice());
	}
	public HashMap<Item,Integer> stock(){
		return stock;
	}
	public HashMap<Clothing,Integer> clothing(){
		return clothingstock;
	}
	protected void displayGoods(){
		for(Item i:stock.keySet()){
			Global.gui().sale(this, i);
		}
		for(Clothing i:clothingstock.keySet()){
			if(!player.has(i)){
				Global.gui().sale(this, i);
			}
		}
	}
	
	protected boolean checkSale(String name){
		for(Item i:stock.keySet()){
			if(name.equals(i.getName())){
				buy(i);
				return true;
			}
		}
		for(Clothing i:clothingstock.keySet()){
			if(name.equals(i.getName())){
				buy(i);
				return true;
			}
		}
		return false;
	}
	public void buy(Item item){
		if(player.money>=stock.get(item)){
			player.money-=stock.get(item);
			player.gain(item);
			acted=true;
			Global.gui().refresh();
		}
		else{
			Global.gui().message("You don't have enough money to purchase that.");
		}
	}
	public void buy(Clothing item){
		if(player.money>=clothingstock.get(item)){
			player.money-=clothingstock.get(item);
			player.gain(item);
			acted=true;
			Global.gui().refresh();
		}
		else{
			Global.gui().message("You don't have enough money to purchase that.");
		}
		
	}
}
