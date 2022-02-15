package main

import (
	"PDAV/internal/decoder"
	"PDAV/internal/encoder"
	"PDAV/internal/model"
	"fmt"
)

func main() {
	image, _ := (&model.PpmFile{Filename: "resources/nt-P3.ppm"}).Read()
	jpeg := encoder.Jpeg{}
	encoded, err := jpeg.Encode(image)
	if err != nil {
		fmt.Println(err.Error())
		return
	}

	image = (&decoder.Jpeg{}).Decode(encoded)
	err = (&model.PpmFile{Filename: "resources/result.ppm"}).Write(image)
	if err != nil {
		fmt.Println(err.Error())
	}
}
