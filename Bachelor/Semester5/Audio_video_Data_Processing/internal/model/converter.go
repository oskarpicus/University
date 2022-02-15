package model

// RGBtoYUV converts the data of a pixel from RGB to YUV.
func (p *Pixel) RGBtoYUV() {
	red, green, blue := p.Item1, p.Item2, p.Item3

	p.Item1 = 0.299*red + 0.587*green + 0.114*blue
	p.Item2 = 128 - 0.169*red - 0.331*green + 0.499*blue
	p.Item3 = 128 + 0.449*red - 0.418*green - 0.0813*blue
}

// YUVtoRGB converts the data of a pixel from YUV / YCbCr to RGB.
func (p *Pixel) YUVtoRGB() {
	y, cB, cR := p.Item1, p.Item2, p.Item3

	p.Item1 = p.clip(y+1.402*(cR-128), 0, 255)
	p.Item2 = p.clip(y-0.344*(cB-128)-0.714*(cR-128), 0, 255)
	p.Item3 = p.clip(y+1.772*(cB-128), 0, 255)
}

// clip returns the clipping value of a float, given an interval.
func (p *Pixel) clip(value, left, right float64) float64 {
	if value < left {
		return left
	}
	if value > right {
		return right
	}
	return value
}
