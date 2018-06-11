package strategy.move.factory;

import level.DifficulityLevel;
import move.strategy.IMoveStrategy;
import move.strategy.impl.VerticalMoveStrategy;

public class MoveStrategyFactory {
	private MoveStrategyFactory(){
		
	}
	
	public static IMoveStrategy getMoveStrategy(DifficulityLevel level){
		IMoveStrategy moveStrategy = null;
		switch (level) {
		case Easy:
			moveStrategy = new VerticalMoveStrategy(0,4);
			break;

		case Meduim:
			//random1
			moveStrategy = new VerticalMoveStrategy(0,12);
			break;

		case Hard:
			moveStrategy = new VerticalMoveStrategy(0,20);
			break;
			
		default:
			break;
		}
		return moveStrategy;
	}
}