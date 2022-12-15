/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: ["https://sea-turtle-app-hqisk.ondigitalocean.app", "storage.googleapis.com"]
  },
  experimental: {
    appDir: true,
  },

}

module.exports = nextConfig
