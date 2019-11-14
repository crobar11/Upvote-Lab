package com.example.upvote;

import java.util.ArrayList;

public class Message
{
    public int score;
    public String key;
    public String body;
    public ArrayList<ReplyMessage> replies;

    public Message(String body, String key)
    {
        this.score = 0;
        this.key = key;
        this.body = body;
        this.replies = new ArrayList<ReplyMessage>();
    }

    public Message()
    {
        body = "NA";
        score = 0;
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

    public String getKey() {return this.key; }

    public String getBody() { return this.body; }


    public ArrayList<ReplyMessage> getReplies()
    {
        return this.replies;
    }

    public void addReply(ReplyMessage reply)
    {
        this.replies.add(reply);
    }

    public void delReply(String replyKey)
    {
        for(int x = 0; x < replies.size(); x++)
        {
            if(replies.get(x).getKey().equals(replyKey))
            {
                replies.remove(x);
                return;
            }
        }
        // handle error (make toast)
    }

    public int compareTo(Message other)
    {
        if(getScore() > other.getScore())
            return 1;
        if(getScore() < other.getScore())
            return -1;
        else
            return 0;
    }

    public String toString()
    {
        String bod = body;
        return bod;
    }

}
