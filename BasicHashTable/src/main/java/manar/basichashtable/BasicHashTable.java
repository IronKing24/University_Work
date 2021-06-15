package manar.basichashtable;

public class BasicHashTable{
    public static void main(String[] args) {
    	HashTable Table = new HashTable(50);
        Table.put("da", "1");
        Table.put("da", "2");
        Table.put("da", "3");
        System.out.println(Table.containsKey("da"));
        System.out.println(Table.get("da"));
        Table.remove("da");
        System.out.println(Table.containsKey("da"));
    }
}

class HashTable{

    int count = 0;
    entry [] table = new entry[50];
    entry currentEntry;

    HashTable(int length){
        this.table = new entry[length];
    }

    //returns the value of the given key
    String get(String key){
       currentEntry = table[index(key)];
        if(currentEntry == null)
            return null;
        else{
            while(!currentEntry.key.equals(key)){
                currentEntry = currentEntry.next;
            }
            return currentEntry.value;
        }
    }
    
    //returns adds a value to the end of the link list at the hash of the key
    void put(String key, String value){
        currentEntry = table[index(key)];
        if(currentEntry == null){
            currentEntry = new entry();
            currentEntry.key = key;
            currentEntry.value = value;
            table[index(key)] = currentEntry;
        }
        else{
            while(currentEntry.next != null){
                if(currentEntry.key.equals(key))
                    return;
                currentEntry = currentEntry.next;
            }
            currentEntry.next = new entry();
            currentEntry.next.key = key;
            currentEntry.next.value = value;
        }
        count++;
    } 
     
    // removes the node with the key from the linked list
    void remove(String key){
        currentEntry = table[index(key)];
        entry previousEntry = null;
        if(currentEntry == null)
            return;
        else if(currentEntry.next == null)
            table[index(key)] = null;
        else{
            while(!currentEntry.key.equals(key) && currentEntry.next !=  null){
                previousEntry = currentEntry;
                currentEntry = currentEntry.next;
            }
            if(currentEntry.key.equals(key))
                previousEntry.next = currentEntry.next;;
        }
        count--;
    } 
    
    // checks whether the key exists in the hash table
    boolean containsKey(String key){
        currentEntry = table[index(key)]; 
        if(currentEntry == null)
        return false;
    else{
        while(!currentEntry.key.equals(key) && currentEntry.next !=  null ){
            currentEntry = currentEntry.next;
        }

        if(currentEntry.key.equals(key))
            return true;
        else
            return false;
    }
    } 
    
    //returns the number of items in the hash table
    int size(){
        return count;
    }

    //method for calculating the index of the key in the array
    int index(Object obj){
        return  Math.abs(obj.hashCode()) % table.length;
    }

    class entry{
       public String value;
       public String key;
       public entry next;
    }
}

