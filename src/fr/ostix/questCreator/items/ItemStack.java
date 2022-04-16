package fr.ostix.questCreator.items;



public class ItemStack {
    private Item item;
    private int count;

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return item.toString();
    }
}
