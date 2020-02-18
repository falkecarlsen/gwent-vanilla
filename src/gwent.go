package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"math/rand"
	"net/http"
	"testing"
)

type Game struct {
	currentRound int // using default of 0
	deck         []Card
	rounds       [3]Round
	players      [2]Player
	winner       *Player
}

type Round struct {
	players [2]Player // Assumes that [2] limits amount of players to two
	winner  *Player
}

type Player struct {
	name      string
	cards     []Card
	alignment Alignment
}

type Card struct {
	value int // ace(0) low, king(12) high
	suit  Suit
}

type Suit int

const (
	Clubs    Suit = iota
	Hearts   Suit = iota
	Diamonds Suit = iota
	Spades   Suit = iota
	Joker    Suit = iota
)

type Alignment int

const (
	Might Alignment = iota
	Magic Alignment = iota
	Mind  Alignment = iota
)

func main() {
	defaultCards := []Card{Card{}, Card{}, Card{}}

	var player1 = Player{
		name:      "Bob",
		cards:     defaultCards,
		alignment: Mind,
	}

	var player2 = Player{
		name:      "Alice",
		cards:     defaultCards,
		alignment: Magic,
	}

	var round1 = Round{players: [2]Player{player1, player2}}

	// do round

	round1.winner = &player1

	game := Game{
		currentRound: 0,
		rounds:       [3]Round{round1},
		players:      [2]Player{player1, player2},
		winner:       nil,
	}

	fmt.Printf("number of cards: %+v\n", len(game.deck))

	fmt.Printf("%+v\n", game)

	//server()
}

func doRound(player1 Player, player2 Player, game Game) {
	// Check if just starting round
	if game.currentRound == 0 {
		generateDeck(3)
	}

	// Check for precondition alignment actions
	if player1.alignment == Mind {

	} else if player2.alignment == Mind {

	}
}

func generateDeck(jokerNum int) []Card {
	var deck []Card
	// Generate a standard deck and append
	for suit := 0; suit <= 3; suit++ {
		for value := 0; value <= 12; value++ {
			deck = append(deck, Card{
				value: value,
				suit:  Suit(suit),
			})
		}
	}

	// Add any jokers
	for joker := 0; joker < jokerNum; joker++ {
		deck = append(deck, Card{
			value: joker,
			suit:  Joker,
		})
	}

	// Shuffle deck
	rand.Shuffle(len(deck), func(i, j int) {
		deck[i], deck[j] = deck[j], deck[i]
	})

	return deck
}

func getRandomCard() Card {
	return Card{
		value: rand.Int() % 13,
		suit:  Suit(rand.Int() % 4),
	}
}

func TestgetRandomCard(t *testing.T) {
	card := getRandomCard()
	if card.value < 0 || card.value > 12 {
		t.Error("Card is not of sane value", card.value)
	} else if card.suit < 0 || card.suit > 3 {
		t.Error("Card is not of sane suit value", card.suit)
	}
}

func mindPickCard(player Player) {
	fmt.Println("Choosing card for player")
}

func server() {

	http.HandleFunc("/", http.FileServer(http.Dir("./public/")).ServeHTTP)

	log.Fatal(http.ListenAndServe(":8080", nil))
}

func fooHandler(w http.ResponseWriter, r *http.Request) {
	bytes, err := ioutil.ReadAll(r.Body)
	if err != nil {
		panic(err)
	}
	fmt.Println(string(bytes))

	fmt.Println(r.Header)
}
