package com.interfacedesign.fd;

import com.interfacedesign.fd.Actors.StormCard;

import java.util.ArrayList;
import java.util.Collections;

public class Stormdeck {
    private ArrayList<StormCard> deck;

    //creates a storm deck, and if it should have a predefined deck or random
    public Stormdeck(boolean stacked){
        if (stacked){
            deck= createPredefiendStormDeck();
        }else{
            deck = createStormDeck();
        }
    }

    //draws first card from deck, if deck is empty creates a new deck and draws a card
    public StormCard drawCard(){
        if (deck.isEmpty()){
            deck = createStormDeck();
        }
        return deck.remove(0);
    }

    //return cards left in deck
    public int cardsLeft(){
        return deck.size();
    }


    //creates random ordered deck
    private ArrayList<StormCard> createStormDeck(){
        ArrayList<StormCard> cards = new ArrayList<>();
        cards.add(new StormCard(1,0,false,false));
        cards.add(new StormCard(2,0,false,false));
        cards.add(new StormCard(3,0,false,false));

        cards.add(new StormCard(-1,0,false,false));
        cards.add(new StormCard(-2,0,false,false));
        cards.add(new StormCard(-3,0,false,false));

        cards.add(new StormCard(0,1,false,false));
        cards.add(new StormCard(0,2,false,false));
        cards.add(new StormCard(0,3,false,false));

        cards.add(new StormCard(0,-1,false,false));
        cards.add(new StormCard(0,-2,false,false));
        cards.add(new StormCard(0,-3,false,false));

        cards.add(new StormCard(1,0,false,false));
        cards.add(new StormCard(2,0,false,false));
        cards.add(new StormCard(3,0,false,false));

        cards.add(new StormCard(-1,0,false,false));
        cards.add(new StormCard(-2,0,false,false));
        cards.add(new StormCard(-3,0,false,false));

        cards.add(new StormCard(0,1,false,false));
        cards.add(new StormCard(0,2,false,false));
        cards.add(new StormCard(0,3,false,false));

        cards.add(new StormCard(0,-1,false,false));
        cards.add(new StormCard(0,-2,false,false));
        cards.add(new StormCard(0,-3,false,false));

        cards.add(new StormCard(0,0,true,false));
        cards.add(new StormCard(0,0,true,false));
        cards.add(new StormCard(0,0,true,false));
        cards.add(new StormCard(0,0,true,false));

        cards.add(new StormCard(0,0,false,true));
        cards.add(new StormCard(0,0,false,true));
        cards.add(new StormCard(0,0,false,true));

        Collections.shuffle(cards);
        System.out.println("Created new Stormdeck");
        return cards;
    }

    //create predefiended deck
    private ArrayList<StormCard> createPredefiendStormDeck(){
        ArrayList<StormCard> cards = new ArrayList<>();
        cards.add(new StormCard(1,0,false,false));
        cards.add(new StormCard(0,0,true,false));
        cards.add(new StormCard(0,0,false,true));
        cards.add(new StormCard(3,0,false,false));
        cards.add(new StormCard(-2,0,false,false));

        cards.add(new StormCard(-1,0,false,false));
        cards.add(new StormCard(0,2,false,false));
        cards.add(new StormCard(2,0,false,false));
        cards.add(new StormCard(0,0,true,false));
        cards.add(new StormCard(-3,0,false,false));

        cards.add(new StormCard(0,3,false,false));
        cards.add(new StormCard(2,0,false,false));

        cards.add(new StormCard(0,-1,false,false));
        cards.add(new StormCard(0,0,false,true));
        cards.add(new StormCard(0,-2,false,false));
        cards.add(new StormCard(0,1,false,false));

        cards.add(new StormCard(1,0,false,false));
        cards.add(new StormCard(0,-3,false,false));

        cards.add(new StormCard(-1,0,false,false));
        cards.add(new StormCard(-2,0,false,false));
        cards.add(new StormCard(0,0,true,false));
        cards.add(new StormCard(0,2,false,false));
        cards.add(new StormCard(3,0,false,false));
        cards.add(new StormCard(-3,0,false,false));

        cards.add(new StormCard(0,1,false,false));
        cards.add(new StormCard(0,-3,false,false));

        cards.add(new StormCard(0,-1,false,false));
        cards.add(new StormCard(0,-2,false,false));
        cards.add(new StormCard(0,3,false,false));

        cards.add(new StormCard(0,0,true,false));

        cards.add(new StormCard(0,0,false,true));

        System.out.println("Created new stacked Stormdeck");
        return cards;
    }

}


