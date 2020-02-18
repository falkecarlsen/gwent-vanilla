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
	players      [2]*Player
	winner       *Player
}

type Round struct {
	winner         *Player
	startingPlayer *Player
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
	var player1 = Player{
		name:      "Bob",
		cards:     nil,
		alignment: Mind,
	}

	var player2 = Player{
		name:      "Alice",
		cards:     nil,
		alignment: Magic,
	}

	playGame(&player1, &player2)

	//server()
}

func printCardsDebug(game Game) {
	fmt.Printf("number of cards: %v\n cards: %+v\n", len(game.deck), game.deck)
	fmt.Printf("player1 num of cards: %v\t, cards:%+v\n", len(game.players[0].cards), game.players[0].cards)
	fmt.Printf("player1 num of cards: %v\t, cards:%+v\n", len(game.players[1].cards), game.players[1].cards)
	fmt.Printf("total number of cards in play: %v\n", len(game.deck)+len(game.players[0].cards)+len(game.players[1].cards))
}

func playGame(player1 *Player, player2 *Player) {
	if player1 == nil || player2 == nil {
		panic("Players cannot be null")
	}

	game := Game{
		currentRound: 0,
		deck:         generateDeck(2),
		rounds:       [3]Round{},
		players:      [2]*Player{player1, player2},
		winner:       nil,
	}

	fmt.Printf("number of cards: %v\n cards: %+v\n", len(game.deck), game.deck)

	// Deal cards
	game.deck = dealCards(player1, player2, game.deck)

	printCardsDebug(game)

	// Discard two dealt cards
	discardTwoCardsGameStart(player1)
	discardTwoCardsGameStart(player2)

	printCardsDebug(game)

	// Check who goes first and choose alignment in order
	var startingPlayerIndex int
	startingPlayerIndex, game.rounds[0].startingPlayer = diceTossStartingPlayer(game)

	if startingPlayerIndex == 0 {
		// Player 2 chooses alignment first
		chooseAlignment(game, 1)
		chooseAlignment(game, 0)
	} else {
		// Player 1 chooses alignment first
		chooseAlignment(game, 0)
		chooseAlignment(game, 1)
	}

	// Play rounds (until pass) - check for win condition after each round
	for i := 0; i < 3; i++ {
		// Check who goes first

		// Do pre-game alignment (Mind)

		
	}
}

func chooseAlignment(game Game, i int) Game {
	alignment := Might
	game.players[i].alignment = alignment
	return game
}

func diceTossStartingPlayer(game Game) (int, *Player) {
	// return pointer to player who goes first
	index := rand.Int() % len(game.players)
	return index, game.players[index]
}

func dealCards(player1 *Player, player2 *Player, deck []Card) []Card {
	for i := 0; i < 24; i++ {
		var card Card
		// pop card from deck
		card, deck = deck[len(deck)-1], deck[:len(deck)-1]
		// add card to player deck
		if i%2 == 0 {
			player1.cards = append(player1.cards, card)
		} else {
			player2.cards = append(player2.cards, card)
		}
	}
	return deck
}

func discardTwoCardsGameStart(player *Player) {
	for i := 0; i < 2; i++ {
		discardCard(player, 0)
	}
}

func discardCard(player *Player, i int) {
	if len(player.cards) < i {
		panic("Cannot remove a card out of bounds for hand")
	}
	// Delete card at i from cards
	player.cards = player.cards[:i+copy(player.cards[i:], player.cards[i+1:])]
}

func checkForWin(game Game) (bool, *Player) {
	for _, player := range game.players {
		playerWins := 0
		for _, round := range game.rounds {
			if round.winner == player {
				playerWins++
			}
		}
		if playerWins >= 2 {
			return true, player
		}
	}
	return false, &Player{}
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

	http.HandleFunc("/", http.FileServer(http.Dir("./src/public/")).ServeHTTP)

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
