package com.example.upvote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReplyMessage
{
    private String text;
    private int score;
    private String key;

    public ReplyMessage(String text, String key)
    {
        this.text = text;
        this.key = key;
        this.score = 0;
    }

    public String getKey()
    {
        return this.key;
    }

    public void incrementScore()
    {
        this.score++;
    }

    public void decrementScore()
    {
        this.score--;
    }

    public int getScore()
    {
        return this.score;
    }

    public String toString()
    {
        return this.text;
    }
}
