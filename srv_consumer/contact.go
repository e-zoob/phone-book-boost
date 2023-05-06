package main

type Contact struct {
	Name   string `json:"name" bson:"name,omitempty"`
	Number int    `json:"number" bson:"number,omitempty"`
}
