package project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import project.view.ScoreBoardView;
import project.view.ScoreboardData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ScoreboardController {
    private static ScoreboardController instance = null;
    private ScoreBoardView scoreBoardView;
    private Socket scoreBoardDataTransferSocket;
    private DataInputStream dataTransferScoreboardInputStream;
    private DataOutputStream dataTransferScoreboardOutputStream;

    private ScoreboardController() {

    }

    public static ScoreboardController getInstance() {
        if (instance == null)
            instance = new ScoreboardController();
        return instance;
    }

    public ScoreBoardView getScoreBoardView() {
        return scoreBoardView;
    }

    public void setScoreBoardView(ScoreBoardView scoreBoardView) {
        System.out.println(scoreBoardView + " is not null");
        this.scoreBoardView = scoreBoardView;
    }

    public void initializeNetworkForScoreboard() {

        try {
            scoreBoardDataTransferSocket = new Socket("localhost", 8000);
            dataTransferScoreboardOutputStream = new DataOutputStream(scoreBoardDataTransferSocket.getOutputStream());
            dataTransferScoreboardInputStream = new DataInputStream(scoreBoardDataTransferSocket.getInputStream());
            dataTransferScoreboardOutputStream.writeUTF("data_transfer_scoreboard " + MainMenuController.getInstance().getLoggedInUserToken());
            dataTransferScoreboardOutputStream.flush();
            startThreadOfScoreboardDataTransfer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startThreadOfScoreboardDataTransfer() {
        new Thread(() -> {
            try {
                while (true) {
                    String in = dataTransferScoreboardInputStream.readUTF();
                    if (in.equals("close"))
                        break;
                    System.out.println("received scoreboard");
                    ArrayList<ScoreboardData> arrayList = new Gson().fromJson(in, new TypeToken<ArrayList<ScoreboardData>>() {
                    }.getType());
                    System.out.println("scoreboard view in thread " + scoreBoardView);
                    ScoreboardData.setDataArrayList(arrayList);
                    //if (scoreBoardView != null)
                    ScoreBoardView scoreBoardView = ScoreboardController.getInstance().getScoreBoardView();
                    Platform.runLater(scoreBoardView::showBoard);
                }
                dataTransferScoreboardInputStream.close();
                dataTransferScoreboardOutputStream.close();
                scoreBoardDataTransferSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void closeScoreboard() {
        try {

            ControllerManager.getInstance().getDataOutputStream().writeUTF("scoreboard close " + MainMenuController.getInstance().getLoggedInUserToken());
            ControllerManager.getInstance().getDataOutputStream().flush();
            ControllerManager.getInstance().getDataInputStream().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
