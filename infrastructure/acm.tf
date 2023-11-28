resource "aws_acm_certificate" "default" {
  provider          = aws.virginia
  domain_name       = var.domain_name
  validation_method = "DNS"
  key_algorithm     = "EC_prime256v1"

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_acm_certificate_validation" "default" {
  provider        = aws.virginia
  certificate_arn = aws_acm_certificate.default.arn
}
