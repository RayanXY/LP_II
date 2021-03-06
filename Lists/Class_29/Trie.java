/**
 * This class represents a simple Trie Tree. 
 *
 * @author Rayan Avelino
 * @version 21.11.2017
 */
import java.util.HashMap;
import java.util.ArrayList;

public class Trie{

	private TrieNode root;

	/**
	 * Creates a Trie Tree.
	 */
	public Trie(){
		root = new TrieNode();
		root.setParent(new TrieNode());
	}
	
	/**
	 * Inserts a word in the tree.
	 * @param word - The word to be inserted.
	 */
	public void insertWord(String word){

		/// Retrieving the root's children.
		HashMap<Character, TrieNode> children = root.getChildren();

		/// The parent node.
		TrieNode parent = root;

		/// Storing the word.
		for(int i = 0; i < word.length(); i++){

			TrieNode currentChar = null;

			char c = word.charAt(i);

			/// Checking if the Key 'c' already exists.
			if(children.containsKey(c)){
				currentChar = children.get(c);
			}else{
				currentChar = new TrieNode(c);
				children.put(c, currentChar);
				currentChar.setParent(parent);
			}

			parent = currentChar;
			children = currentChar.getChildren();

			/// When it reaches the last character
			if(i == (word.length()-1)){
				currentChar.setEndOfWord(true);
			}			

		}

	}

	/**
	 * This method searchs a word in the tree.
	 * @param word - The word to be searched.
	 */
	public boolean search(String word){

		TrieNode w = searcher(word);

		if(w != null && w.isEndOfWord()){
			return true;
		}

		return false;

	}

	/**
	 * This method removes a word in the tree.
	 * @param word - The word to be searched.
	 */
	public boolean remove(String word){

		TrieNode currentChar = searcher(word);

		if(currentChar != null && currentChar.isEndOfWord()){

			TrieNode parent = currentChar.getParent();
			char c = currentChar.getCharacter();

			/// Deleting when there is one child.
			while(parent.getChildren().size() == 1){
				parent.resetChildren();
				currentChar = parent;
				c = currentChar.getCharacter();
				parent = currentChar.getParent();
			}

			/// Deleting the remaining
			char delete = c;
			parent.getChildren().entrySet().removeIf(e -> e.getKey().equals(delete));

			return true;

		}

		return false;

	}

	/**
	 * This method helps the search and remove methods.
	 * @param word - The word to be searched.
	 */
	public TrieNode searcher(String word){

		HashMap<Character, TrieNode> children = root.getChildren();

		TrieNode currentChar = null;

		for(int i = 0; i < word.length(); i++){

			char c = word.charAt(i);

			if(children.containsKey(c)){
				currentChar = children.get(c);
				children = currentChar.getChildren();
			}else{
				return null;
			}

		}

		return currentChar;

	}

	/**
	 * Prints all the words in the tree.
	 * @return An ArrayList of the words.
	 */
	public ArrayList<String> print(){

		ArrayList<String> words = new ArrayList<String>();

		for(TrieNode n : root.getChildren().values()){
			if(n != null){
				print(words, Character.toString(n.getCharacter()) + "", n);
			}
		}

		return words;

	}

	/**
	 * The recursive portion of the fucntion above.
	 * @param words - An ArrayList to store the words.
	 * @param word - The word about to be checked.
	 * @param n - The current node.
	 */
	private void print(ArrayList<String> words, String word, TrieNode n){

		if(n.isEndOfWord()){
			words.add(word);
		}

		for(TrieNode t : n.getChildren().values()){
			if( t != null){
				print(words, word + Character.toString(t.getCharacter()), t);
			}
		}

	}

}