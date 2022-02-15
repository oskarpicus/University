package model

// Image represents an abstraction of an image.
type Image struct {
	// Pixels represents the pixel matrix, with the corresponding triplets.
	Pixels [][]*Pixel
	// Width represents the width of the image, in number of pixels.
	Width int
	// Height represents the height of the image, in number of pixels.
	Height int
}
