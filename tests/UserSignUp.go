package main

type UserSignUp struct {
	Login      string `json:"login"`
	Password   string `json:"password"`
	LastName   string `json:"lastName"`
	Patronymic string `json:"patronymic"`
	Name       string `json:"name"`
}
