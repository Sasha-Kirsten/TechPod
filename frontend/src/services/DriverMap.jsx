import React, {useState, useEffect} from 'react';
import {GoogleMap, Marker, useLoadScript} from '@react-google-maps/api';

const mapContainerStyle = {
  width: '100%',
  height: '400px',
};

const center = {
  lat: 37.7749, // Default latitude (San Francisco)
  lng: -122.4194, // Default longitude (San Francisco)
};

const DriverMap = ({ deliveryPoints, driverLocation }) => {
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: 'YOUR_GOOGLE_MAPS_API_KEY', // Replace with your Google Maps API key
  });
  if (loadError) return <div>Error loading maps</div>;
  if (!isLoaded) return <div>Loading Maps</div>;

  return (
    <GoogleMap
      mapContainerStyle={mapContainerStyle}
      zoom={12}
      center={driverLocation || center}
    >
      {deliveryPoints.map((point, index) => (
        <Marker key={index} position={{ lat: point.lat, lng: point.lng }}
        label="📍"
        />
      ))}
      {driverLocation.map((location, index) => (
        <Marker
          key={index}
          position={{ lat: location.lat, lng: location.lng }}
          label="🚚"
        //   icon={{
        //     url: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png',
        //   }}
        />
      ))}
    </GoogleMap>
  );
};

export default DriverMap;