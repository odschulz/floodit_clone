package core;

import core.config.GameStatus;
import core.interfaces.GamePlayMessage;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AbstractBoard2DTests {

    @Test
    public void getGameStatusMessageTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AbstractBoard2D board = Mockito.mock(AbstractBoard2D.class);
        GamePlayMessage messanger = new DefaultGamePlayMessage();
        Mockito.when(board.getMessages()).thenReturn(messanger);

        Mockito.when(board.getMoveCount()).thenReturn(1);
        Mockito.when(board.getMaxMoves()).thenReturn(2);
        Mockito.when(board.isCompleted()).thenReturn(false);
        Assert.assertEquals("Message should be for game in progress.", messanger.gameInProgressMessage(1, 2), board.getGameStatusMessage());

        Mockito.when(board.getMoveCount()).thenReturn(2);
        Assert.assertEquals("Message should be for lost game.", messanger.gameLoseMessage(2, 2), board.getGameStatusMessage());

        Mockito.when(board.isCompleted()).thenReturn(true);
        Assert.assertEquals("Message should be for won game.",messanger.gameWinMessage(2, 2), board.getGameStatusMessage());
    }

    @Test
    public void getGameStatusTest() {
        AbstractBoard2D board = Mockito.mock(AbstractBoard2D.class);
        Mockito.when(board.getMoveCount()).thenReturn(1);
        Mockito.when(board.getMaxMoves()).thenReturn(2);
        Mockito.when(board.isCompleted()).thenReturn(false);
        Assert.assertEquals("Game should be in progress.", GameStatus.IN_PROGRESS, board.getGameStatus());

        Mockito.when(board.getMoveCount()).thenReturn(2);
        Assert.assertEquals("Game should be lost.", GameStatus.LOST, board.getGameStatus());

        Mockito.when(board.isCompleted()).thenReturn(true);
        Assert.assertEquals("Game should be won.", GameStatus.WON, board.getGameStatus());
    }
}
