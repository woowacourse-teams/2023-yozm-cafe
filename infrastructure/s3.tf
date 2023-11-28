resource "aws_s3_bucket" "default" {
  bucket = var.bucket_name
}

resource "aws_s3_bucket_public_access_block" "default" {
  bucket = aws_s3_bucket.default.id

  block_public_acls       = true
  block_public_policy     = true
  restrict_public_buckets = true
  ignore_public_acls      = true
}

resource "aws_s3_bucket_policy" "default" {
  bucket     = aws_s3_bucket.default.id
  depends_on = [aws_s3_bucket_public_access_block.default]
  policy     = data.aws_iam_policy_document.default.json
}

resource "aws_s3_object" "images" {
  bucket = aws_s3_bucket.default.id
  key    = "images/"
}

resource "aws_s3_object" "app" {
  bucket = aws_s3_bucket.default.id
  key    = "app/"
}

data "aws_iam_policy_document" "default" {
  statement {
    sid = "Allow all HTTP from cloudfront"
    principals {
      type        = "Service"
      identifiers = ["cloudfront.amazonaws.com"]
    }
    actions = [
      "s3:GetObject"
    ]
    resources = [
      "${aws_s3_bucket.default.arn}/*"
    ]
    effect = "Allow"
    condition {
      test     = "StringEquals"
      variable = "AWS:SourceArn"
      values   = [aws_cloudfront_distribution.default.arn]
    }
  }
}
