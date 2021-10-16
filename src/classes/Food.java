package classes;

import java.io.Serializable;

/**
 * Object representing food sold in the restaurant extending from the MenuItem
 * class. Food sold must be a main course, dessert or drinks.
 * 
 * @author soh jun jie
 * @version 1.0
 * @since 2016-10-24
 */
public class Food extends MenuItem implements Serializable {

	public enum CourseType {
		MAIN_COURSE, DESSERT, DRINKS
	};

	private static final long serialVersionUID = 4883217370046073402L;

	/**
	 * Course type of the food. Uses CourseType enum: MAIN_COURSE, DESSERT, DRINKS
	 */
	private CourseType type;

	/**
	 * Create a new food having menu name, description, price and course type.
	 * 
	 * @param menuName Name of the food as appear on the menu
	 * @param desc     Description of the food
	 * @param price    Price of the food
	 * @param type     Course type of the food
	 */
	public Food(String menuName, String desc, double price, CourseType type) {
		super(menuName, desc, price);
		this.type = type;
	}

	/**
	 * Get the course type of the food
	 * 
	 * @return this food course type
	 */
	public CourseType getType() {
		return this.type;
	}

	/**
	 * Changes the course type of the food
	 * 
	 * @param type Course type of the food.
	 */
	public void setType(CourseType type) {
		this.type = type;
	}

}
