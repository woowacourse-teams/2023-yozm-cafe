variable "project_name" {
  type        = string
  description = "The unique name of the project (IMPORTANT: this value will be used for tagging) (e.g. yozm-cafe)"
}

variable "bucket_name" {
  type        = string
  description = "The name of the target bucket where static files will be deployed. (e.g. yozm-cafe)"
}

variable "domain_name" {
  type        = string
  description = "The domain name that will be connected to CloudFront. (e.g. yozm.cafe)"
}

variable "api_domain_name" {
  type        = string
  description = "The domain name that will be used for API request. (e.g. api.yozm.cafe)"
}
