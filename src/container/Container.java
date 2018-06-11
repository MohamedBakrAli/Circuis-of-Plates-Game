package container;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Container<T> {
	private ArrayList<T> array;
	private int index;
	private Iterator<T> iterator;
	public Container() {
		array = new ArrayList<T>();
		index = 0;
		iterator = new ContainerIterator();
	}
	
	public void add(T obj){
		array.add(obj);
	}
	
	public void remove(int index){
		array.remove(index);
	}
	
	public void remove(T obj){
		array.remove(obj);
	}
	
	public Iterator getIterator(){
		return this.iterator;
	}
	
	public void clear(){
		this.array.clear();
		index = 0;
	}
	
	public int size(){
		return this.array.size();
	}
	
	public T get(int index){
		return array.get(index);
	}
	
	private class ContainerIterator implements Iterator<T>{

		@Override
		public boolean hasNext() {
			if(index == array.size())return false;
			return true;
		}

		@Override
		public T next() {
			if(hasNext()){
				return array.get(index++);
			}
			return null;
		}
	}

}

