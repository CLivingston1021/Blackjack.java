

//Christopher Livingston
//CS145
//07/17/22
//LAB 4 DeckofCards "Blackjack"
//creates a game of blackjack that the user can bet with and has a dealer that the player goes against
//for extra credit I learned how to use packages, enum class, switch case and finally got a better
//introduction on using intellij for this assignment








package com.cs145.blackjackdeck;

import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args){

        //welcome and intro to game
        System.out.println("Welcome to Blackjack");

        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffle();
        //create a deck for player
        Deck playerDeck = new Deck();

        Deck dealerDeck = new Deck();

        double playerMoney = 100.00;

        Scanner userInput = new Scanner(System.in);

        //Game loop
        while(playerMoney > 0){
            //keeps playing
            //take players bet
            System.out.println("You have $" + playerMoney + ", how much are you betting?");
            double playerBet = userInput.nextDouble();
            if (playerBet > playerMoney){
                System.out.println("Looks like you are broke game over");
                break;
            }
            boolean endRound = false;

            //Start dealing cards
            //player gets two cards
            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);

            //dealer gets two cards
            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);

            while(true){
                System.out.println("Your hand");
                System.out.print(playerDeck.toString());
                System.out.println("You're at:" + playerDeck.cardsValue());

                //display dealer hand
                System.out.println("Dealer hand: " + dealerDeck.getCard(0).toString() + " and [HIDDEN]");

                //what does the player do?
                System.out.println("Would you like to (1) HIT or (2) STAND?");
                int repsonse = userInput.nextInt();

                //they hit
                if(repsonse == 1){
                        playerDeck.draw(playingDeck);
                        System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());
                        //bust if > 21
                    if(playerDeck.cardsValue() > 21){
                        System.out.println("Bust: Currently valued at: " + playerDeck.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;

                    }
                }
                if(repsonse == 2){
                    break;
                }
            }
            //reveal dealer cards
            System.out.println("Dealers Cards: " + dealerDeck.toString());
            //see if dealer wins or lost
            if((dealerDeck.cardsValue() > playerDeck.cardsValue()) && endRound == false){
                System.out.println("Dealer has won round");
                playerMoney -= playerBet;
                endRound = true;

            }
            //dealer draws at 16, and stands at 17
            while(((dealerDeck.cardsValue() < 17) && endRound == false)){
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
            }
            //display total value for dealer
            System.out.println("Dealer's hand is at: " + dealerDeck.cardsValue());
            //determine if dealer busted
            if((dealerDeck.cardsValue() > 21) && endRound == false){
                System.out.println("Dealer bust! round won!!");
                playerMoney += playerBet;
                endRound = true;
            }
            if((playerDeck.cardsValue() == dealerDeck.cardsValue()) && endRound == false){
                System.out.println("Push");
                endRound = true;
            }
            if((playerDeck.cardsValue() > dealerDeck.cardsValue()) && endRound == false){
                System.out.println("You won the hand");
                playerMoney += playerBet;
                endRound = true;
            }
            else if (endRound == false){
                System.out.println("you lose the hand");
                playerMoney -= playerBet;
                endRound = true;
            }
            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("End of hand.");
        }
        System.out.println("Game over you lost your money. :(");

    }
}
