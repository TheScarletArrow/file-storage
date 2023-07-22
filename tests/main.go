package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"net/url"
	"strings"
)

type Tokens struct {
	AccessToken  string `json:"access_token"`
	RefreshToken string `json:"refresh_token"`
}

func sendHttpPost(login string, password string) *Tokens {
	// The URL to send the request to
	myUrl := "http://localhost:8080/login"

	// The data to be sent as x-www-form-urlencoded fields
	data := url.Values{}
	data.Set("username", login)
	data.Set("password", password)

	// Create a new HTTP client
	client := &http.Client{}

	// Send the POST request
	response, err := client.Post(myUrl, "application/x-www-form-urlencoded",
		strings.NewReader(data.Encode()))
	if err != nil {
		fmt.Println("Error sending POST request:", err)
		//t.Error("Error sending POST request", err)
	}

	defer response.Body.Close()

	// Read the response body
	var tokens Tokens
	err = json.NewDecoder(response.Body).Decode(&tokens)
	if tokens.AccessToken == "" || tokens.RefreshToken == "" {
		//t.Error("Expected non-nil value for tokens, got nil")
	}

	// Print the response
	return &tokens
}
